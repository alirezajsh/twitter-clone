package com.example.demo5.controller;

import com.example.demo5.models.*;
import com.example.demo5.network.NetworkPacket;
import com.example.demo5.network.NetworkConnection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminController {

    private static NetworkConnection connection;
    private static boolean connected = false;

    // کش محلی
    private static User currentUser;
    private static List<Post> replyPosts = new ArrayList<>();
    private static List<User> allUsers = new ArrayList<>();
    private static List<AdminPostView> allPostViews = new ArrayList<>();
    private static List<Hashtag> allHashtags = new ArrayList<>();
    private static List<AdminReportView> allReportViews = new ArrayList<>();

    // تنظیم اتصال (از بیرون توسط HelloController یا AdminPanel)
    public static void setConnection(NetworkConnection conn) {
        connection = conn;
        connected = true;
    }

    private static NetworkPacket sendAndReceive(NetworkPacket request) throws Exception {
        if (!connected || connection == null) {
            throw new Exception("Not connected to server");
        }
        connection.send(request);
        return connection.listen();
    }

    // ---------- متدهای بلاک/آنبلاک ----------
    public static void blockUser(User user) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.BLOCK_USER, user.getUserId());
            sendAndReceive(request);
            user.setIsBlocked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unblockUser(User user) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.UNBLOCK_USER, user.getUserId());
            sendAndReceive(request);
            user.setIsBlocked(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void blockPost(Post post) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.BLOCK_POST, post.getPostId());
            sendAndReceive(request);
            post.block();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unblockPost(Post post) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.UNBLOCK_POST, post.getPostId());
            sendAndReceive(request);
            post.unblock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- متدهای دریافت اطلاعات کاربر ----------
    public static String getUserFullName(int i) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_FULLNAME, i);
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getUserName(int i) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_NAME, i);
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getUserTypeById(int userId) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, userId);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof User) {
                User u = (User) response.getData();
                if (u instanceof BlueUser) return "BLUE";
                else if (u instanceof GoldUser) return "GOLD";
                else return "NORMAL";
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------- متدهای پاسخ ----------
    public static int getUserReplyPostNumber() {
        return replyPosts.size();
    }

    public static void setCurrentUser(int id) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, id);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof User) {
                currentUser = (User) response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateReplies1() {
        updateReplies(currentUser.getUserId());
    }

    public static void updateReplies(int userId) {
        replyPosts.clear();
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_REPLIES_FOR_POST, userId);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<Post> replies = (List<Post>) response.getData();
                replyPosts.addAll(replies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Post getFatherPostByReplyIndex(int replyIndex) {
        Post reply = getReplyByIndex(replyIndex);
        if (reply != null) {
            try {
                NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, reply.getFatherPostId());
                NetworkPacket response = sendAndReceive(request);
                if (response.getData() instanceof Post) {
                    return (Post) response.getData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Post getReplyByIndex(int index) {
        if (index >= 0 && index < replyPosts.size()) {
            return replyPosts.get(index);
        }
        return null;
    }

    // ---------- اطلاعات کاربر فعلی ----------
    public static String getCurrentUserName() {
        return (currentUser != null) ? currentUser.getUserName() : "";
    }

    public static String getCurrentUserFullName() {
        return (currentUser != null) ? currentUser.getFullName() : "";
    }

    public static String getCurrentUSerSymbol() {
        return (currentUser != null) ? currentUser.getSymbolImage() : "";
    }

    public static Date getCurrentUserMadeDate() {
        return (currentUser != null) ? currentUser.getMadeDate() : null;
    }

    public static int getCurrentUserFollowersNumber() {
        return (currentUser != null) ? currentUser.getFollowerNumber() : 0;
    }

    public static int getCurrentUserFollowingNumber() {
        return (currentUser != null) ? currentUser.getFollowingNumber() : 0;
    }

    public static Post getUserPostByIndex(int i) {
        if (currentUser == null) return null;
        int postId = currentUser.getPost(i);
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, postId);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                return (Post) response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Post getUserReplies(int i) {
        return (i >= 0 && i < replyPosts.size()) ? replyPosts.get(i) : null;
    }

    public static String getCurrentUserBiography() {
        return (currentUser != null) ? currentUser.getBiographi() : "";
    }

    public static int getCurrentUserPostNumber() {
        return (currentUser != null) ? currentUser.getPostNumber() : 0;
    }

    // ---------- به‌روزرسانی کش ----------
    public static void refreshAdminData() {
        refreshUsers();
        refreshPosts();
        refreshHashtags();
        refreshReports();
    }

    public static void refreshUsers() {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_ALL_USERS, null);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<User> users = (List<User>) response.getData();
                allUsers.clear();
                allUsers.addAll(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshPosts() {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_ALL_POSTS, null);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<Post> posts = (List<Post>) response.getData();
                allPostViews.clear();
                for (Post p : posts) {
                    User writer = null;
                    try {
                        NetworkPacket reqUser = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, p.getWritterId());
                        NetworkPacket resUser = sendAndReceive(reqUser);
                        if (resUser.getData() instanceof User) {
                            writer = (User) resUser.getData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    allPostViews.add(new AdminPostView(p, writer));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshHashtags() {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_ALL_HASHTAGS, null);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<Hashtag> hashtags = (List<Hashtag>) response.getData();
                allHashtags.clear();
                allHashtags.addAll(hashtags);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshReports() {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_ALL_POST_REPORTS, null);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<PostReport> reports = (List<PostReport>) response.getData();
                allReportViews.clear();
                for (PostReport r : reports) {
                    Post post = null;
                    User writer = null;
                    try {
                        NetworkPacket reqPost = new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, r.getReportedId());
                        NetworkPacket resPost = sendAndReceive(reqPost);
                        if (resPost.getData() instanceof Post) {
                            post = (Post) resPost.getData();
                            if (post != null) {
                                NetworkPacket reqUser = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, post.getWritterId());
                                NetworkPacket resUser = sendAndReceive(reqUser);
                                if (resUser.getData() instanceof User) {
                                    writer = (User) resUser.getData();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    allReportViews.add(new AdminReportView(r, post, writer));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- متدهای دسترسی به داده‌ها ----------
    public static int getUserNumbere() {
        return allUsers.size();
    }

    public static User getUserByIndex(int index) {
        if (index >= 0 && index < allUsers.size()) {
            return allUsers.get(index);
        }
        return null;
    }

    public static int getPostNumber() {
        return allPostViews.size();
    }

    public static AdminPostView getPostByIndex(int index) {
        if (index >= 0 && index < allPostViews.size()) {
            return allPostViews.get(index);
        }
        return null;
    }

    public static int getHashtagNumber() {
        return allHashtags.size();
    }

    public static Hashtag getHashtagByIndex(int index) {
        if (index >= 0 && index < allHashtags.size()) {
            return allHashtags.get(index);
        }
        return null;
    }

    public static Hashtag getHashtagsById(int id) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_HASHTAG_BY_ID, id);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Hashtag) {
                return (Hashtag) response.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static int getPostReportsNumber() {
        return allReportViews.size();
    }

    public static AdminReportView getPostReportByIndex(int index) {
        if (index >= 0 && index < allReportViews.size()) {
            return allReportViews.get(index);
        }
        return null;
    }

    // ---------- کلاس‌های داخلی ----------
    public static class AdminPostView {
        private final Post post;
        private final User writer;

        public AdminPostView(Post post, User writer) {
            this.post = post;
            this.writer = writer;
        }

        public String getPostText() { return post.getPostText(); }
        public String getUserName() { return writer != null ? writer.getUserName() : ""; }
        public String getFullName() { return writer != null ? writer.getFullName() : ""; }
        public String getSymbol() { return writer != null ? writer.getSymbolImage() : ""; }
        public int getLikesNumber() { return post.getLikesNumber(); }
        public int getRetweetCount() { return post.getRetweetCount(); }
        public int getCommentsNumber() { return post.getCommentsNumber(); }
        public int getSawNumber() { return post.getSawNumber(); }
    }

    public static class AdminReportView {
        private final PostReport report;
        private final Post post;
        private final User writer;

        public AdminReportView(PostReport report, Post post, User writer) {
            this.report = report;
            this.post = post;
            this.writer = writer;
        }

        public String getPostText() { return post != null ? post.getPostText() : ""; }
        public String getUserName() { return writer != null ? writer.getUserName() : ""; }
        public String getFullName() { return writer != null ? writer.getFullName() : ""; }
        public String getSymbol() { return writer != null ? writer.getSymbolImage() : ""; }
    }
}