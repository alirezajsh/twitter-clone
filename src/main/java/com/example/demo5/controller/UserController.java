package com.example.demo5.controller;



import com.example.demo5.exceptions.*;
import com.example.demo5.models.*;
import com.example.demo5.network.ClientNetworkConnection;
import com.example.demo5.network.NetworkConnection;
import com.example.demo5.network.NetworkPacket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserController {


    private static List<Message> chatMessages = new ArrayList<>();

    private static NetworkConnection connection;
    private static boolean connected = false;

    public static User currentUser;
    public static User currentUser2;
    public static ArrayList<String> allUsername = new ArrayList<>();
    public static List<Post> homePosts = new ArrayList<>();
    public static Post currentPost;

    public static boolean connectToServer(String host, int port) {
        try {
            connection = new ClientNetworkConnection();
            connected = connection.connect(host, port);
            return connected;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        if (connection != null) {
            connection.disconnect();
            connected = false;
        }
    }

    private static NetworkPacket sendAndReceive(NetworkPacket request) throws Exception {
        if (!connected) {
            throw new Exception("Not connected to server");
        }
        connection.send(request);
        return connection.listen();
    }

    public static NetworkConnection getConnection() {
        return connection;
    }

    // ---------- احراز هویت ----------
    public static String getSignUpText(String fullName, String userName, String phoneNumber, String email, String password, String confirmPassword) {
        try {
            if (fullName.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty() ||
                    email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                return "you don't fill all text field.";
            }
            if (!phoneNumber.matches("^09\\d{9}$")) {
                return "the phone number is not correct.";
            }
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return "email is not correct";
            }
            if (!password.equals(confirmPassword)) {
                return "password is not correct.";
            }

            User newUser = new BasicUser(fullName, phoneNumber, userName, email, password);
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.SIGNUP, newUser);
            NetworkPacket response = sendAndReceive(request);

            if (response.getType() == NetworkPacket.RequestType.SIGNUP) {
                Object data = response.getData();
                if (data instanceof User) {
                    currentUser = (User) data;
                    return "";
                } else {
                    return "Signup failed!";
                }
            }
            return "Signup failed!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    //احراز هویت برای ورود
    public static String getLoginText(String userNameOrEmail, String password) {
        try {
            if (userNameOrEmail.equals("Admin") && password.equals("Admin")) {
                return "Admin";
            }
            if (userNameOrEmail.isEmpty() || password.isEmpty()) {
                return "you didn't fill all.";
            }

            String[] creds = {userNameOrEmail, password};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.LOGIN, creds);
            NetworkPacket response = sendAndReceive(request);

            if (response.getType() == NetworkPacket.RequestType.LOGIN) {
                Object data = response.getData();
                if (data instanceof User) {
                    currentUser = (User) data;
                    return "";
                } else {
                    return "Login failed!";
                }
            }
            return "Login failed!";
        } catch (Exception e) {
            if (e.getMessage().contains("UserNotFoundException")) {
                return "user name or email is not found.";
            } else if (e.getMessage().contains("WrongPasswordException")) {
                return "password is not correct.";
            }
            return "Error: " + e.getMessage();
        }
    }

    // ---------- لایک ----------
    public static void likePost(int userId, int postId) {
        try {
            int[] ids = {userId, postId};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.LIKE_POST, ids);
            sendAndReceive(request);
        } catch (Exception e) {
            System.out.println("Like failed: " + e.getMessage());
        }
    }

    public static Post toggleLike(Post post) {
        if (post == null || currentUser == null) return null;
        try {
            NetworkPacket.RequestType type;
            if (isLikedByCurrentUser(post)) {
                type = NetworkPacket.RequestType.UNLIKE_POST;
            } else {
                type = NetworkPacket.RequestType.LIKE_POST;
            }
            int[] ids = {currentUser.getUserId(), post.getPostId()};
            NetworkPacket request = new NetworkPacket(type, ids);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                Post updatedPost = (Post) response.getData();
                if (currentPost != null && currentPost.getPostId() == updatedPost.getPostId()) {
                    currentPost = updatedPost;
                }

                for (int i = 0; i < homePosts.size(); i++) {
                    if (homePosts.get(i).getPostId() == updatedPost.getPostId()) {
                        homePosts.set(i, updatedPost);
                        break;
                    }
                }
                return updatedPost;
            }
        } catch (Exception e) {
            System.out.println("Toggle like failed: " + e.getMessage());
        }
        return null;
    }

    public static boolean isLikedByCurrentUser(Post post) {
        if (currentUser == null || post == null) return false;
        if (post.getLikes() != null) {
            for (int id : post.getLikes()) {
                if (id == currentUser.getUserId()) return true;
            }
        }
        return false;
    }

    public static void followPerson(int follower, int followed) {
        try {
            int[] ids = {follower, followed};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.FOLLOW, ids);
            sendAndReceive(request);
            if (currentUser != null && currentUser.getUserId() == follower) {
                currentUser.addFollowing(followed);
            }
        } catch (Exception e) {
            System.out.println("Follow failed: " + e.getMessage());
        }
    }

    public static void unfollowPerson(int unfollowerId, int unfollowedId) {
        try {
            int[] ids = {unfollowerId, unfollowedId};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.UNFOLLOW, ids);
            sendAndReceive(request);
            if (currentUser != null && currentUser.getUserId() == unfollowerId) {
                currentUser.getFollowing().remove(Integer.valueOf(unfollowedId));
            }
        } catch (Exception e) {
            System.out.println("Unfollow failed: " + e.getMessage());
        }
    }

    public static void unfollowPerson(User unfollower, User unfollowed) {
        unfollowPerson(unfollower.getUserId(), unfollowed.getUserId());
    }

    public static void buyBlueUser(int id, User user) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.BUY_BLUE, user);
            NetworkPacket response = sendAndReceive(request);
            Object data = response.getData();
            if (data instanceof User) {
                currentUser = (User) data;
            } else {
                System.out.println("Buy blue failed: " + data);
            }
        } catch (Exception e) {
            System.out.println("Buy blue failed: " + e.getMessage());
        }
    }

    public static void buyGoldUser(int id, User user) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.BUY_GOLD, user);
            NetworkPacket response = sendAndReceive(request);
            Object data = response.getData();
            if (data instanceof User) {
                currentUser = (User) data;
            } else {
                System.out.println("Buy gold failed: " + data);
            }
        } catch (Exception e) {
            System.out.println("Buy gold failed: " + e.getMessage());
        }
    }

    public static void addPost(String postContent, String photoAddress, String videoAddress, String hashtag) {
        try {
            Post post = new Post();
            post.setPostText(postContent);
            post.setWritterId(currentUser.getUserId());
            post.setMadeDate(new Date());

            if (hashtag != null && !hashtag.isEmpty()) {
                String tagText = hashtag.startsWith("#") ? hashtag.substring(1) : hashtag;
                NetworkPacket req = new NetworkPacket(NetworkPacket.RequestType.GET_HASHTAG_BY_NAME, tagText);
                NetworkPacket res = sendAndReceive(req);
                Hashtag existing = (Hashtag) res.getData();
                int hashtagId;
                if (existing == null) {
                    Hashtag newTag = new Hashtag(tagText);
                    hashtagId = -1;
                } else {
                    hashtagId = existing.getHashtagId();
                }
                ArrayList<Integer> tags = new ArrayList<>();
                if (hashtagId != -1) tags.add(hashtagId);
                post.setHashtags(tags);
            }

            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.CREATE_POST, post);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                Post created = (Post) response.getData();

                homePosts.add(0, created);
                if (currentUser != null) {
                    NetworkPacket userRequest = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, currentUser.getUserId());
                    NetworkPacket userResponse = sendAndReceive(userRequest);
                    if (userResponse.getData() instanceof User) {
                        currentUser = (User) userResponse.getData();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Add post failed: " + e.getMessage());
        }
    }

    public static void addAnswerPost(String postContent, String hashtags) {
        try {
            if (currentPost == null) return;
            Post reply = new Post();
            reply.setPostText(postContent);
            reply.setWritterId(currentUser.getUserId());
            reply.setFatherPostId(currentPost.getPostId());
            reply.setMadeDate(new Date());

            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.REPLY_POST, reply);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                Post createdReply = (Post) response.getData();
                if (currentPost != null) {
                    currentPost.addAnswerPost(createdReply.getPostId());
                }
                for (int i = 0; i < homePosts.size(); i++) {
                    if (homePosts.get(i).getPostId() == currentPost.getPostId()) {
                        homePosts.set(i, currentPost);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Add reply failed: " + e.getMessage());
        }
    }


    public static Post getCurrentPost(){return currentPost;}

    // ---------- فید خانه ----------
    public static void refreshHomePost() {
        try {
            if (currentUser == null) return;
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_FEED, currentUser.getUserId());
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<Post> feed = (List<Post>) response.getData();
                homePosts.clear();
                homePosts.addAll(feed);
            }
        } catch (Exception e) {
            System.out.println("Refresh feed failed: " + e.getMessage());
        }
    }

    public static void fillAllUsername() {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_ALL_USERS, null);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<User> users = (List<User>) response.getData();
                allUsername.clear();
                for (User u : users) {
                    if (currentUser != null && !u.getUserName().equals(currentUser.getUserName())) {
                        allUsername.add(u.getUserName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Fill usernames failed: " + e.getMessage());
        }
    }

    // ---------- بررسی دنبال کردن ----------
    public static boolean areYouFollowHim() {
        if (currentUser == null || currentUser2 == null) return false;
        try {
            int[] ids = {currentUser.getUserId(), currentUser2.getUserId()};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.CHECK_FOLLOW, ids);
            NetworkPacket response = sendAndReceive(request);
            return (boolean) response.getData();
        } catch (Exception e) {
            return false;
        }
    }

    // ---------- متدهای Getter/Setter ----------
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(int id) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_BY_ID, id);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof User) {
                currentUser = (User) response.getData();
            }
        } catch (Exception e) {
            System.out.println("Set current user failed: " + e.getMessage());
        }
    }

    public static void setCurrentUser2(User user) {
        currentUser2 = user;
    }

    public static User getCurrentUser2() {
        return currentUser2;
    }

    public static void setCurrentUserBirthDate(Date birthDate) {
        if (currentUser != null) {
            currentUser.setBirthDate(birthDate);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserFullName(String fullName) {
        if (!fullName.isEmpty() && currentUser != null) {
            currentUser.setFullName(fullName);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserName(String userName) {
        if (!userName.isEmpty() && currentUser != null) {
            currentUser.setUserName(userName);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^09\\d{9}$") && currentUser != null) {
            currentUser.setPhoneNumber(phoneNumber);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserEmail(String email) {
        if (!email.isEmpty() && currentUser != null) {
            currentUser.setEmail(email);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserBiographi(String biographi) {
        if (currentUser != null) {
            currentUser.setBiographi(biographi);
            updateUser(currentUser);
        }
    }

    public static void setCurrentUserPassword(String password, String confirmPassword) {
        if (!password.isEmpty() && password.equals(confirmPassword) && currentUser != null) {
            currentUser.setPassword(password);
            updateUser(currentUser);
        }
    }

    public static void restockCurrentUser(int price) {
        if (currentUser != null) {
            currentUser.increaseInitialBalance(price);
            updateUser(currentUser);
        }
    }

    private static void updateUser(User user) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.UPDATE_USER, user);
            sendAndReceive(request);
        } catch (Exception e) {
            System.out.println("Update user failed: " + e.getMessage());
        }
    }

    // ---------- متدهای دریافت اطلاعات پست و کاربر ----------
    public static String getWritterSymbol(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_SYMBOL, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getWritterName(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_FULLNAME, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getWritterUserName(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_NAME, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPostMadeDate(Post post) {
        return post != null ? post.getMadeDate().toString() : "";
    }

    public static String getPostText(Post post) {
        return post != null ? post.getPostText() : "";
    }

    public static int getLikesNumber(Post post) {
        return post != null ? post.getLikesNumber() : 0;
    }

    public static int getCommentsNumber(Post post) {
        return post != null ? post.getCommentsNumber() : 0;
    }

    public static int getRetweetNumber(Post post) {
        return post != null ? post.getRetweetCount() : 0;
    }

    public static int getSawNumber(Post post) {
        return post != null ? post.getSawNumber() : 0;
    }

    public static void increaseRetweet(Post post) {
        if (post != null && currentUser != null) {
            try {
                int[] ids = {currentUser.getUserId(), post.getPostId()};
                NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.RETWEET, ids);
                sendAndReceive(request);
                post.increaseRetweet(currentUser.getUserId());
            } catch (Exception e) {
                System.out.println("Retweet failed: " + e.getMessage());
            }
        }
    }

    public static void openPost(Post post) {
        if (post != null) {
            try {
                NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.VIEW_POST, post.getPostId());
                sendAndReceive(request);
                post.seePost();
            } catch (Exception e) {
                System.out.println("View post failed: " + e.getMessage());
            }
        }
    }

    public static void setCurrentPost(Post post) {
        currentPost = post;
    }

    // ---------- جستجو ----------
    public static User findUserByUsername(String userName) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.FIND_USER_BY_USERNAME, userName);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof User) {
                return (User) response.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static User findUserByUsernameOrEmail(String userNameOrEmail) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.FIND_USER_BY_USERNAME_OR_EMAIL, userNameOrEmail);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof User) {
                return (User) response.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static Post getPostById(int id) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, id);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                return (Post) response.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // ---------- هشتگ ----------
    public static Hashtag isHashtagExist(String hashtag) {
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_HASHTAG_BY_NAME, hashtag);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Hashtag) {
                return (Hashtag) response.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setUserHashtags(ArrayList<Integer> hashtags) {
        if (currentUser != null) {
            try {
                Object[] params = {currentUser.getUserId(), hashtags};
                NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.SET_USER_HASHTAGS, params);
                sendAndReceive(request);
                currentUser.setUserHashtags(hashtags);
            } catch (Exception e) {
                System.out.println("Set hashtags failed: " + e.getMessage());
            }
        }
    }

    // ---------- متدهای مربوط به صفحه کاربر دیگر ----------
    public static String getPostText2(int i) {
        if (currentUser2 == null || currentUser2.getPosts().size() <= i) return "";
        Post p = getPostById(currentUser2.getPosts().get(i));
        return p != null ? p.getPostText() : "";
    }

    public static String getCurrentUser2FullName() {
        return currentUser2 != null ? currentUser2.getFullName() : "";
    }

    public static String getCurrentUser2Name() {
        return currentUser2 != null ? currentUser2.getUserName() : "";
    }

    public static String getCurrentUser2Symbol() {
        return currentUser2 != null ? currentUser2.getSymbolImage() : "";
    }

    public static int getCurrentUser2PostSawNumberById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getSawNumber() : 0;
    }

    public static int getCurrentUser2PostCommentNumberById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getCommentsNumber() : 0;
    }

    public static int getCurrentUser2PostRetweetNumberById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getRetweetCount() : 0;
    }

    public static int getCurrentUser2PostLikeNumberById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getLikesNumber() : 0;
    }

    public static String getCurrentUser2PostMadeDateById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getMadeDate().toString() : "";
    }

    public static String getCurrentUser2PostContentById(int i) {
        Post p = getCurrentUser2PostById(i);
        return p != null ? p.getPostText() : "";
    }

    public static int getCurrentUser2PostNumber() {
        return currentUser2 != null ? currentUser2.getPosts().size() : 0;
    }

    public static Post getCurrentUser2PostById(int i) {
        if (currentUser2 == null || currentUser2.getPosts().size() <= i) return null;
        return getPostById(currentUser2.getPosts().get(i));
    }

    // ---------- متدهای کاربر فعلی ----------
    public static String getCurrentUserFullName() {
        return currentUser != null ? currentUser.getFullName() : "";
    }

    public static String getCurrentUserName() {
        return currentUser != null ? currentUser.getUserName() : "";
    }

    public static String getCurrentUserSymbol() {
        return currentUser != null ? currentUser.getSymbolImage() : "";
    }

    public static int getCurrentUserPostSawNumberById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getSawNumber() : 0;
    }

    public static int getCurrentUserPostCommentNumberById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getCommentsNumber() : 0;
    }

    public static int getCurrentUserPostRetweetNumberById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getRetweetCount() : 0;
    }

    public static int getCurrentUserPostLikeNumberById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getLikesNumber() : 0;
    }

    public static String getCurrentUserPostMadeDateById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getMadeDate().toString() : "";
    }

    public static String getCurrentUserPostContentById(int i) {
        Post p = getCurrentUserPostById(i);
        return p != null ? p.getPostText() : "";
    }

    public static int getCurrentUserPostNumber() {
        return currentUser != null ? currentUser.getPosts().size() : 0;
    }

    public static Post getCurrentUserPostById(int i) {
        if (currentUser == null || currentUser.getPosts().size() <= i) return null;
        return getPostById(currentUser.getPosts().get(i));
    }

    public static String getPostText(int i) {
        return getCurrentUserPostContentById(i);
    }

    public static ArrayList<Integer> getCurrentUserPosts() {
        return currentUser != null ? currentUser.getPosts() : new ArrayList<>();
    }

    // ---------- هوم ----------
    public static String getHomePostContent(int i) {
        return i < homePosts.size() ? homePosts.get(i).getPostText() : "";
    }

    public static String getHomePostWritter(int i) {
        if (i >= homePosts.size()) return "";
        try {
            Post p = homePosts.get(i);
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_FULLNAME, p.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getHomePostUserName(int i) {
        if (i >= homePosts.size()) return "";
        try {
            Post p = homePosts.get(i);
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_NAME, p.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static int getHomePostNumber() {
        return homePosts.size();
    }

    // ---------- پاسخ‌ها ----------
    public static int getReplyPostNumber(Post post) {
        return post != null ? post.getRetweetCount() : 0;
    }

    public static String getReplyPostContent(int i, Post post) {
        if (post == null || post.getRetweetCount() <= i) return "";
        try {
            int replyId = post.getReplyByIndex(i);
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_POST_BY_ID, replyId);
            NetworkPacket response = sendAndReceive(request);
            if (response.getData() instanceof Post) {
                return ((Post) response.getData()).getPostText();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static int getPostCommentNumber(Post post) {
        return post != null ? post.getCommentsNumber() : 0;
    }

    public static int getPostLikeNumber(Post post) {
        return post != null ? post.getLikesNumber() : 0;
    }

    public static String getPostContent(Post post) {
        return post != null ? post.getPostText() : "";
    }

    public static String getPostFullName(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_FULLNAME, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPostSymbol(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_SYMBOL, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPostUserName(Post post) {
        if (post == null) return "";
        try {
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_USER_NAME, post.getWritterId());
            NetworkPacket response = sendAndReceive(request);
            return (String) response.getData();
        } catch (Exception e) {
            return "";
        }
    }







    // متد برای دریافت تعداد پیام‌های چت
    public static int getChatMessageNumber() {
        return chatMessages.size();
    }

    // متد برای دریافت پیام با ایندکس
    public static Message getChatMessage(int index) {
        if (index >= 0 && index < chatMessages.size()) {
            return chatMessages.get(index);
        }
        return null;
    }

    // متد برای دریافت محتوای پیام با ایندکس
    public static String getChatMessageContent(int index) {
        Message msg = getChatMessage(index);
        return msg != null ? msg.getContent() : "";
    }

    // متد برای تشخیص اینکه پیام توسط کاربر فعلی فرستاده شده یا نه
    public static boolean isChatMessageSentByCurrentUser(int index) {
        Message msg = getChatMessage(index);
        if (msg == null || currentUser == null) return false;
        return msg.isSentByUser(currentUser.getUserId());
    }

    // متد برای دریافت زمان پیام
    public static String getChatMessageTime(int index) {
        Message msg = getChatMessage(index);
        return msg != null ? msg.getFormattedTime() : "";
    }

    // متد برای دریافت نام فرستنده پیام
    public static String getChatMessageSenderName(int index) {
        Message msg = getChatMessage(index);
        if (msg == null) return "";

        try {
            if (msg.isSentByUser(currentUser.getUserId())) {
                return "You";
            } else {
                // فرستنده کاربر دیگر است (currentUser2)
                return currentUser2 != null ? currentUser2.getUserName() : "Unknown";
            }
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // متد برای ارسال پیام جدید
    public static void sendChatMessage(String content) {
        if (currentUser == null || currentUser2 == null || content.trim().isEmpty()) {
            return;
        }

        try {
            // ایجاد پیام
            Message message = new Message(currentUser.getUserId(), currentUser2.getUserId(), content);
            chatMessages.add(message);

            // ارسال به سرور
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.SEND_CHAT_MESSAGE, message);
            NetworkPacket response = sendAndReceive(request);

            // اگر پیام با موفقیت ذخیره شد، ID آن را به‌روزرسانی کن
            if (response.getData() instanceof Message) {
                Message savedMessage = (Message) response.getData();
                // جایگزینی پیام با نسخه ذخیره شده (که ID دارد)
                chatMessages.set(chatMessages.size() - 1, savedMessage);
            }

        } catch (Exception e) {
            System.out.println("Send message failed: " + e.getMessage());
            // در صورت خطا، آخرین پیام را از لیست حذف کن
            if (!chatMessages.isEmpty()) {
                chatMessages.remove(chatMessages.size() - 1);
            }
        }
    }


    public static void loadChatHistory(int userId1, int userId2) {
        try {
            int[] params = {userId1, userId2};
            NetworkPacket request = new NetworkPacket(NetworkPacket.RequestType.GET_CHAT_HISTORY, params);
            NetworkPacket response = sendAndReceive(request);

            if (response.getData() instanceof List) {
                @SuppressWarnings("unchecked")
                List<Message> messages = (List<Message>) response.getData();
                chatMessages.clear();
                chatMessages.addAll(messages);
                System.out.println("Loaded " + chatMessages.size() + " messages");
            }
        } catch (Exception e) {
            System.out.println("Load chat history failed: " + e.getMessage());
        }
    }

    // متد برای پاک کردن پیام‌ها (وقتی کاربر تغییر می‌کند)
    public static void clearChatMessages() {
        chatMessages.clear();
    }

    // متد برای دریافت تعداد پیام‌های خوانده نشده
    public static int getUnreadMessageCount() {
        if (currentUser == null) return 0;
        int count = 0;
        for (Message msg : chatMessages) {
            if (!msg.isRead() && !msg.isSentByUser(currentUser.getUserId())) {
                count++;
            }
        }
        return count;
    }






    private static Thread listenerThread;
    private static volatile boolean listening = false;

    public static void startMessageListener() {
        if (listening) return;
        listening = true;
        listenerThread = new Thread(() -> {
            while (listening && connected) {
                try {
                    NetworkPacket packet = connection.listen();
                    if (packet.getType() == NetworkPacket.RequestType.NEW_MESSAGE) {
                        Object data = packet.getData();
                        if (data instanceof Message) {
                            Message msg = (Message) data;

                            if (currentUser2 != null &&
                                    ((msg.getSenderId() == currentUser.getUserId() && msg.getReceiverId() == currentUser2.getUserId()) ||
                                            (msg.getSenderId() == currentUser2.getUserId() && msg.getReceiverId() == currentUser.getUserId()))) {
                                chatMessages.add(msg);

                                javafx.application.Platform.runLater(() -> {


                                });
                            }
                        }
                    } else if (packet.getType() == NetworkPacket.RequestType.DISCONNECT) {
                        break;
                    }
                } catch (Exception e) {
                    if (listening) e.printStackTrace();
                    break;
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public static void stopMessageListener() {
        listening = false;
        if (listenerThread != null) {
            listenerThread.interrupt();
            listenerThread = null;
        }
    }





}