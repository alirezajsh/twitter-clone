package com.example.demo5.network;

import com.example.demo5.exceptions.*;
import com.example.demo5.models.*;
import com.example.demo5.repository.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private volatile boolean running = true;

    private UserRepository userRepo = new UserRepository();
    private PostRepository postRepo = new PostRepository();
    private HashtagRepository hashtagRepo = new HashtagRepository();
    private ReportRepository reportRepo = new ReportRepository();
    private MessageRepository messageRepo = new MessageRepository();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            while (running) {
                NetworkPacket packet = (NetworkPacket) in.readObject();
                NetworkPacket response = handleRequest(packet);
                out.writeObject(response);
                out.flush();
            }
        }catch (SocketException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private NetworkPacket handleRequest(NetworkPacket packet) {
        NetworkPacket.RequestType type = packet.getType();
        Object data = packet.getData();
        try {
            switch (type) {
                // ---------- احراز هویت ----------
                case LOGIN: {
                    String[] creds = (String[]) data;
                    User user = userRepo.login(creds[0], creds[1]);
                    return new NetworkPacket(NetworkPacket.RequestType.LOGIN, user);
                }
                case SIGNUP: {
                    User user = (User) data;
                    boolean success = userRepo.add(user);
                    return new NetworkPacket(NetworkPacket.RequestType.SIGNUP, success ? user : "ERROR");
                }

                // ---------- پست ----------
                case CREATE_POST: {
                    Post post = (Post) data;
                    boolean success = postRepo.add(post);
                    return new NetworkPacket(NetworkPacket.RequestType.CREATE_POST, success ? post : "ERROR");
                }
                case REPLY_POST: {
                    Post reply = (Post) data;
                    boolean success = postRepo.add(reply);
                    if (success) {
                        // افزایش تعداد کامنت‌های پست والد
                        if (reply.getFatherPostId() != 0) {
                            postRepo.incrementCommentCount(reply.getFatherPostId());
                        }
                        return new NetworkPacket(NetworkPacket.RequestType.REPLY_POST, reply);
                    } else {
                        return new NetworkPacket(NetworkPacket.RequestType.REPLY_POST, "ERROR");
                    }
                }

                case LIKE_POST: {
                    int[] ids = (int[]) data;
                    int userId = ids[0];
                    int postId = ids[1];
                    // اضافه کردن به جدول user_likes (در صورت عدم وجود)
                    try (Connection conn = MySQLConnection.getConnection()) {
                        PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO user_likes (user_id, post_id) VALUES (?, ?) " +
                                        "ON DUPLICATE KEY UPDATE user_id = user_id" // جلوگیری از خطای تکراری
                        );
                        ps.setInt(1, userId);
                        ps.setInt(2, postId);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        // اگر خطا رخ داد، نادیده بگیر (مثلاً اگر قبلاً وجود داشت)
                    }
                    postRepo.incrementLikeCount(postId);
                    Post updatedPost = postRepo.findById(postId);
                    return new NetworkPacket(NetworkPacket.RequestType.LIKE_POST, updatedPost);
                }

                case UNLIKE_POST: {
                    int[] ids = (int[]) data;
                    int userId = ids[0];
                    int postId = ids[1];
                    // حذف از جدول user_likes
                    try (Connection conn = MySQLConnection.getConnection()) {
                        PreparedStatement ps = conn.prepareStatement(
                                "DELETE FROM user_likes WHERE user_id = ? AND post_id = ?"
                        );
                        ps.setInt(1, userId);
                        ps.setInt(2, postId);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        // نادیده بگیر
                    }
                    postRepo.decrementLikeCount(postId);
                    Post updatedPost = postRepo.findById(postId);
                    return new NetworkPacket(NetworkPacket.RequestType.UNLIKE_POST, updatedPost);
                }
                case RETWEET: {
                    int[] ids = (int[]) data;
                    postRepo.incrementRetweetCount(ids[1]);
                    return new NetworkPacket(NetworkPacket.RequestType.RETWEET, "OK");
                }
                case VIEW_POST: {
                    int postId = (int) data;
                    postRepo.incrementViewCount(postId);
                    return new NetworkPacket(NetworkPacket.RequestType.VIEW_POST, "OK");
                }
                case GET_FEED: {
                    int userId = (int) data;
                    List<Post> feed = postRepo.getFeedForUser(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_FEED, (java.io.Serializable) feed);
                }
                case GET_POST_BY_ID: {
                    int postId = (int) data;
                    Post post = postRepo.findById(postId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, post);
                }
                case GET_REPLIES_FOR_POST: {
                    int postId = (int) data;
                    List<Post> replies = postRepo.getRepliesForPost(postId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_REPLIES_FOR_POST, (java.io.Serializable) replies);
                }
                case GET_ALL_POSTS: {
                    List<Post> posts = postRepo.findAll();
                    return new NetworkPacket(NetworkPacket.RequestType.GET_ALL_POSTS, (java.io.Serializable) posts);
                }

                // ---------- کاربران ----------
                case FOLLOW: {
                    int[] ids = (int[]) data;
                    boolean success = userRepo.followUser(ids[0], ids[1]);
                    if (success) {
                        return new NetworkPacket(NetworkPacket.RequestType.FOLLOW, "OK");
                    } else {
                        return new NetworkPacket(NetworkPacket.RequestType.FOLLOW, "ERROR");
                    }
                }
                case UNFOLLOW: {
                    int[] ids = (int[]) data;
                    boolean success = userRepo.unfollowUser(ids[0], ids[1]);
                    if (success) {
                        return new NetworkPacket(NetworkPacket.RequestType.UNFOLLOW, "OK");
                    } else {
                        return new NetworkPacket(NetworkPacket.RequestType.UNFOLLOW, "ERROR");
                    }
                }
                case CHECK_FOLLOW: {
                    int[] ids = (int[]) data;
                    boolean follows = userRepo.isFollowing(ids[0], ids[1]);
                    return new NetworkPacket(NetworkPacket.RequestType.CHECK_FOLLOW, follows);
                }
                case GET_ALL_USERS: {
                    List<User> users = userRepo.findAll();
                    return new NetworkPacket(NetworkPacket.RequestType.GET_ALL_USERS, (java.io.Serializable) users);
                }
                case GET_USER_BY_ID: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, user);
                }
                case FIND_USER_BY_USERNAME: {
                    String username = (String) data;
                    try {
                        User user = userRepo.findByUsername(username);
                        return new NetworkPacket(NetworkPacket.RequestType.FIND_USER_BY_USERNAME, user);
                    } catch (UserNotFoundException e) {
                        return new NetworkPacket(NetworkPacket.RequestType.DISCONNECT, "NOT_FOUND");
                    }
                }
                case FIND_USER_BY_USERNAME_OR_EMAIL: {
                    String input = (String) data;
                    User user = null;
                    try {
                        user = userRepo.findByUsername(input);
                    } catch (UserNotFoundException e) {
                        user = userRepo.findByEmail(input);
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.FIND_USER_BY_USERNAME_OR_EMAIL, user);
                }
                case UPDATE_USER: {
                    User user = (User) data;
                    boolean success = userRepo.update(user);
                    return new NetworkPacket(NetworkPacket.RequestType.UPDATE_USER, success ? "OK" : "ERROR");
                }
                case GET_USER_BY_USERNAME: {
                    String username = (String) data;
                    try {
                        User user = userRepo.findByUsername(username);
                        return new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_USERNAME, user);
                    } catch (UserNotFoundException e) {
                        return new NetworkPacket(NetworkPacket.RequestType.DISCONNECT, "NOT_FOUND");
                    }
                }

                // ---------- اشتراک‌ها ----------
                case BUY_BLUE: {
                    User user = (User) data;
                    if (user.getInitialBalance() < 9) {
                        return new NetworkPacket(NetworkPacket.RequestType.BUY_BLUE, "INSUFFICIENT");
                    }
                    User updated = new BlueUser(user.getFullName(), user.getPhoneNumber(),
                            user.getUserName(), user.getEmail(), user.getPassword());
                    updated.setUserId(user.getUserId());
                    updated.setInitialBalance(user.getInitialBalance() - 9);
                    updated.setMadeDate(user.getMadeDate());
                    userRepo.update(updated);
                    return new NetworkPacket(NetworkPacket.RequestType.BUY_BLUE, updated);
                }
                case BUY_GOLD: {
                    User user = (User) data;
                    if (user.getInitialBalance() < 19) {
                        return new NetworkPacket(NetworkPacket.RequestType.BUY_GOLD, "INSUFFICIENT");
                    }
                    User updated = new GoldUser(user.getFullName(), user.getPhoneNumber(),
                            user.getUserName(), user.getEmail(), user.getPassword());
                    updated.setUserId(user.getUserId());
                    updated.setInitialBalance(user.getInitialBalance() - 19);
                    updated.setMadeDate(user.getMadeDate());
                    userRepo.update(updated);
                    return new NetworkPacket(NetworkPacket.RequestType.BUY_GOLD, updated);
                }

                // ---------- هشتگ ----------
                case SET_USER_HASHTAGS: {
                    Object[] params = (Object[]) data;
                    int userId = (int) params[0];
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> hashtags = (ArrayList<Integer>) params[1];
                    try (var conn = MySQLConnection.getConnection()) {
                        var ps = conn.prepareStatement("DELETE FROM user_hashtags WHERE user_id = ?");
                        ps.setInt(1, userId);
                        ps.executeUpdate();
                        for (int hid : hashtags) {
                            ps = conn.prepareStatement("INSERT INTO user_hashtags (user_id, hashtag_id) VALUES (?, ?)");
                            ps.setInt(1, userId);
                            ps.setInt(2, hid);
                            ps.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return new NetworkPacket(NetworkPacket.RequestType.SET_USER_HASHTAGS, "ERROR");
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.SET_USER_HASHTAGS, "OK");
                }
                case GET_HASHTAG_BY_ID: {
                    int id = (int) data;
                    Hashtag h = hashtagRepo.findById(id);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_HASHTAG_BY_ID, h);
                }
                case GET_HASHTAG_BY_NAME: {
                    String name = (String) data;
                    Hashtag h = hashtagRepo.findByText(name);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_HASHTAG_BY_NAME, h);
                }
                case GET_ALL_HASHTAGS: {
                    List<Hashtag> hashtags = hashtagRepo.findAll();
                    return new NetworkPacket(NetworkPacket.RequestType.GET_ALL_HASHTAGS, (java.io.Serializable) hashtags);
                }

                // ---------- گزارش‌ها ----------
                case REPORT_POST: {
                    PostReport report = (PostReport) data;
                    reportRepo.add(report);
                    return new NetworkPacket(NetworkPacket.RequestType.REPORT_POST, "OK");
                }
                case REPORT_USER: {
                    UserReport report = (UserReport) data;
                    try (var conn = MySQLConnection.getConnection()) {
                        var ps = conn.prepareStatement("INSERT INTO user_report (reporter_id, reported_user_id, explanation) VALUES (?, ?, ?)");
                        ps.setInt(1, report.getReporterId());
                        ps.setInt(2, report.getReportedId());
                        ps.setString(3, report.getExplanation());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return new NetworkPacket(NetworkPacket.RequestType.REPORT_USER, "ERROR");
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.REPORT_USER, "OK");
                }
                case GET_ALL_POST_REPORTS: {
                    List<PostReport> reports = reportRepo.findAllPostReports();
                    return new NetworkPacket(NetworkPacket.RequestType.GET_ALL_POST_REPORTS, (java.io.Serializable) reports);
                }
                case GET_ALL_USER_REPORTS: {
                    return new NetworkPacket(NetworkPacket.RequestType.GET_ALL_USER_REPORTS, new ArrayList<>());
                }
                case DELETE_POST_REPORT: {
                    int id = (int) data;
                    reportRepo.delete(id);
                    return new NetworkPacket(NetworkPacket.RequestType.DELETE_POST_REPORT, "OK");
                }
                case DELETE_USER_REPORT: {
                    return new NetworkPacket(NetworkPacket.RequestType.DELETE_USER_REPORT, "OK");
                }

                // ---------- ادمین ----------
                case BLOCK_USER: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    if (user != null) {
                        user.setBlocked(true);
                        userRepo.update(user);
                        return new NetworkPacket(NetworkPacket.RequestType.BLOCK_USER, "OK");
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.BLOCK_USER, "ERROR");
                }
                case UNBLOCK_USER: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    if (user != null) {
                        user.setBlocked(false);
                        userRepo.update(user);
                        return new NetworkPacket(NetworkPacket.RequestType.UNBLOCK_USER, "OK");
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.UNBLOCK_USER, "ERROR");
                }
                case BLOCK_POST: {
                    int postId = (int) data;
                    postRepo.updateBlockStatus(postId, true);
                    return new NetworkPacket(NetworkPacket.RequestType.BLOCK_POST, "OK");
                }
                case UNBLOCK_POST: {
                    int postId = (int) data;
                    postRepo.updateBlockStatus(postId, false);
                    return new NetworkPacket(NetworkPacket.RequestType.UNBLOCK_POST, "OK");
                }
                case REFRESH_ADMIN_DATA: {
                    return new NetworkPacket(NetworkPacket.RequestType.REFRESH_ADMIN_DATA, "OK");
                }

                // ---------- اطلاعات اضافی ----------
                case GET_USER_FULLNAME: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_FULLNAME, user != null ? user.getFullName() : "");
                }
                case GET_USER_NAME: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_NAME, user != null ? user.getUserName() : "");
                }
                case GET_USER_SYMBOL: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_SYMBOL, user != null ? user.getSymbolImage() : "");
                }
                case GET_USER_BIOGRAPHY: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_BIOGRAPHY, user != null ? user.getBiographi() : "");
                }
                case GET_USER_FOLLOWERS_COUNT: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_FOLLOWERS_COUNT, user != null ? user.getFollowerNumber() : 0);
                }
                case GET_USER_FOLLOWING_COUNT: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_FOLLOWING_COUNT, user != null ? user.getFollowingNumber() : 0);
                }
                case GET_USER_POST_COUNT: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_POST_COUNT, user != null ? user.getPostNumber() : 0);
                }
                case GET_USER_POST_BY_INDEX: {
                    Object[] params = (Object[]) data;
                    int userId = (int) params[0];
                    int index = (int) params[1];
                    User user = userRepo.findById(userId);
                    if (user != null && user.getPosts() != null && index < user.getPosts().size()) {
                        int postId = user.getPosts().get(index);
                        Post post = postRepo.findById(postId);
                        return new NetworkPacket(NetworkPacket.RequestType.GET_USER_POST_BY_INDEX, post);
                    }
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_POST_BY_INDEX, null);
                }
                case GET_USER_POSTS_LIST: {
                    int userId = (int) data;
                    User user = userRepo.findById(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_USER_POSTS_LIST, user != null ? user.getPosts() : new ArrayList<>());
                }

                // ===================== بخش چت =====================
                case SEND_CHAT_MESSAGE: {
                    Message message = (Message) data;
                    boolean success = messageRepo.add(message);
                    return new NetworkPacket(NetworkPacket.RequestType.SEND_CHAT_MESSAGE, success ? message : "ERROR");
                }
                case GET_CHAT_HISTORY: {
                    int[] ids = (int[]) data;
                    int userId1 = ids[0];
                    int userId2 = ids[1];
                    List<Message> messages = messageRepo.getChatHistory(userId1, userId2);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_CHAT_HISTORY, (java.io.Serializable) messages);
                }
                case GET_UNREAD_MESSAGES: {
                    int userId = (int) data;
                    List<Message> unreadMessages = messageRepo.getUnreadMessages(userId);
                    return new NetworkPacket(NetworkPacket.RequestType.GET_UNREAD_MESSAGES, (java.io.Serializable) unreadMessages);
                }
                case MARK_MESSAGE_AS_READ: {
                    int messageId = (int) data;
                    boolean success = messageRepo.markMessageAsRead(messageId);
                    return new NetworkPacket(NetworkPacket.RequestType.MARK_MESSAGE_AS_READ, success ? "OK" : "ERROR");
                }

                default:
                    return new NetworkPacket(NetworkPacket.RequestType.DISCONNECT, "Unknown command");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new NetworkPacket(NetworkPacket.RequestType.DISCONNECT, "ERROR: " + e.getMessage());
        }
    }

    private void closeResources() {
        try { if (in != null) in.close(); } catch (Exception ignored) {}
        try { if (out != null) out.close(); } catch (Exception ignored) {}
        try { if (socket != null) socket.close(); } catch (Exception ignored) {}
    }
}