/***package com.example.demo5.models;

import com.example.demo5.repository.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataBase {


    // Counters
    private static int userNumber = 0;
    private static int postNumber = 0;
    private static int hashtagNumber = 0;
    private static int reportNumber = 0;

    // Separate ID generator for new hashtags (the first 6 already have IDs 0..5)
    private static int nextHashtagId;

    public static void getUserNumberFormDataBase() {
        try (Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COALESCE(MAX(id), -1) + 1 AS next_id FROM users");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userNumber = rs.getInt("next_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------
    // Static initializer: insert default hashtags and synchronize all counters with DB
    // -------------------------------------------------------------------------------
    static {
        try (Connection conn = getConfiguredConnection()) {
            // --- Insert default hashtags if table is empty ---
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM hashtags");
            ResultSet rsCheck = check.executeQuery();
            rsCheck.next();
            if (rsCheck.getInt(1) == 0) {
                String[] tags = {"iran", "bye", "hello", "hi", "tweet", "tweeter"};
                PreparedStatement ins = conn.prepareStatement("INSERT INTO hashtags (id, title) VALUES (?, ?)");
                for (int i = 0; i < tags.length; i++) {
                    ins.setInt(1, i);
                    ins.setString(2, tags[i]);
                    ins.executeUpdate();
                }
            }

            // --- Synchronize hashtag counters ---
            PreparedStatement maxHashtagPs = conn.prepareStatement("SELECT COALESCE(MAX(id), -1) FROM hashtags");
            ResultSet maxHashtagRs = maxHashtagPs.executeQuery();
            if (maxHashtagRs.next()) {
                int max = maxHashtagRs.getInt(1);
                nextHashtagId = max + 1;
            }
            PreparedStatement cntHashtagPs = conn.prepareStatement("SELECT COUNT(*) FROM hashtags");
            ResultSet cntHashtagRs = cntHashtagPs.executeQuery();
            if (cntHashtagRs.next()) {
                hashtagNumber = cntHashtagRs.getInt(1);
            }

            // --- Synchronize user counters ---
            PreparedStatement maxUserPs = conn.prepareStatement("SELECT COALESCE(MAX(id), -1) FROM users");
            ResultSet maxUserRs = maxUserPs.executeQuery();
            if (maxUserRs.next()) {
                int max = maxUserRs.getInt(1);
                userNumber = max + 1;
            }

            // --- Synchronize post counters ---
            PreparedStatement maxPostPs = conn.prepareStatement("SELECT COALESCE(MAX(id), -1) FROM posts");
            ResultSet maxPostRs = maxPostPs.executeQuery();
            if (maxPostRs.next()) {
                int max = maxPostRs.getInt(1);
                postNumber = max + 1;
            }

            // --- Synchronize report counter (sum of both tables) ---
            PreparedStatement cntReportPs1 = conn.prepareStatement("SELECT COUNT(*) FROM post_report");
            ResultSet cntReportRs1 = cntReportPs1.executeQuery();
            int count1 = 0;
            if (cntReportRs1.next()) count1 = cntReportRs1.getInt(1);

            PreparedStatement cntReportPs2 = conn.prepareStatement("SELECT COUNT(*) FROM user_report");
            ResultSet cntReportRs2 = cntReportPs2.executeQuery();
            int count2 = 0;
            if (cntReportRs2.next()) count2 = cntReportRs2.getInt(1);

            reportNumber = count1 + count2;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Helper: get connection with NO_AUTO_VALUE_ON_ZERO ----------
    private static Connection getConfiguredConnection() throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO'");
        }
        return conn;
    }

    // ---------- Helper: insert a File row and return generated id ----------
    private static int insertFile(Connection conn, File file) throws SQLException {
        String sql = "INSERT INTO files (file_address, file_type, photo_format, video_format, video_time, video_keifiat) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, file.getFileAddress());
        if (file instanceof Photo) {
            ps.setString(2, "PHOTO");
            ps.setString(3, ((Photo) file).getPhotoFormat().name());
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        } else if (file instanceof Video) {
            Video v = (Video) file;
            ps.setString(2, "VIDEO");
            ps.setNull(3, Types.VARCHAR);
            ps.setString(4, v.getVideoFormat().name());
            ps.setDouble(5, v.getTime());
            ps.setString(6, v.getKeifiat() != null ? v.getKeifiat().name() : null);
        } else {
            throw new SQLException("Unknown file type");
        }
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        }
        throw new SQLException("Failed to insert file");
    }

    // ---------- Helper: map User subclass to DB user_type enum ----------
    private static String getUserTypeString(User user) {
        if (user instanceof BasicUser) return "NORMAL";
        if (user instanceof BlueUser) return "BLUE";
        if (user instanceof GoldUser) return "GOLD";
        if (user instanceof PremiumUser) return "PREMIUM";
        return "NORMAL";
    }

    public static String getUserTypeById(int userId) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT user_type FROM users WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("user_type");
            }
            return null; // کاربر با این شناسه یافت نشد
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------- Helper: insert relations (followers, following, likes, chosen hashtags) ----------
    private static void insertUserRelations(Connection conn, User user) throws SQLException {
        for (int fid : user.followers) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user_followers (user_id, follower_id) VALUES (?,?)");
            ps.setInt(1, user.userId);
            ps.setInt(2, fid);
            ps.executeUpdate();
        }
        for (int fid : user.following) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user_followers (user_id, follower_id) VALUES (?,?)");
            ps.setInt(1, fid);
            ps.setInt(2, user.userId);
            ps.executeUpdate();
        }
        for (int postId : user.likedList) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user_likes (user_id, post_id) VALUES (?,?)");
            ps.setInt(1, user.userId);
            ps.setInt(2, postId);
            ps.executeUpdate();
        }
        try {
            java.lang.reflect.Field f = User.class.getDeclaredField("choseHashtags");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<Integer> hashtags = (ArrayList<Integer>) f.get(user);
            if (hashtags != null) {
                for (int hid : hashtags) {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO user_hashtags (user_id, hashtag_id) VALUES (?,?)");
                    ps.setInt(1, user.userId);
                    ps.setInt(2, hid);
                    ps.executeUpdate();
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // ======================== PUBLIC METHODS (unchanged signatures) ========================

    public static User getUserByIndex(int index) {
        return loadUser(index);
    }

    public static void setUserById(int id, User user) {
        try (Connection conn = getConfiguredConnection()) {
            conn.createStatement().executeUpdate("DELETE FROM user_followers WHERE user_id = " + id + " OR follower_id = " + id);
            conn.createStatement().executeUpdate("DELETE FROM user_likes WHERE user_id = " + id);
            conn.createStatement().executeUpdate("DELETE FROM user_hashtags WHERE user_id = " + id);

            String sql = "UPDATE users SET full_name=?, phone_number=?, user_name=?, email=?, password=?, birth_date=?, made_date=?, initial_balance=?, token_number=?, biography=?, profile_file_id=?, is_blocked=?, user_type=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.fullName);
            ps.setString(2, user.phoneNumber);
            ps.setString(3, user.userName);
            ps.setString(4, user.email);
            ps.setString(5, user.password);
            ps.setDate(6, user.birthDate != null ? new java.sql.Date(user.birthDate.getTime()) : null);
            ps.setTimestamp(7, user.madeDate != null ? new java.sql.Timestamp(user.madeDate.getTime()) : null);
            ps.setInt(8, user.initialBalance);
            ps.setInt(9, user.tokenNumber);
            ps.setString(10, user.biographi);
            if (user.profile != null) {
                int fileId = insertFile(conn, user.profile);
                ps.setInt(11, fileId);
            } else {
                ps.setNull(11, Types.INTEGER);
            }
            ps.setBoolean(12, user.isBlocked);
            ps.setString(13, getUserTypeString(user));
            ps.setInt(14, id);
            ps.executeUpdate();

            insertUserRelations(conn, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        try (Connection conn = getConfiguredConnection()) {
            Integer profileFileId = null;
            if (user.profile != null) {
                profileFileId = insertFile(conn, user.profile);
            }
            String sql = "INSERT INTO users (id, full_name, phone_number, user_name, email, password, birth_date, made_date, initial_balance, token_number, biography, profile_file_id, is_blocked, user_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getUserName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            ps.setDate(7, user.birthDate != null ? new java.sql.Date(user.birthDate.getTime()) : null);
            ps.setTimestamp(8, user.madeDate != null ? new java.sql.Timestamp(user.madeDate.getTime()) : null);
            ps.setInt(9, user.initialBalance);
            ps.setInt(10, user.tokenNumber);
            ps.setString(11, user.biographi);
            if (profileFileId != null) {
                ps.setInt(12, profileFileId);
            } else {
                ps.setNull(12, Types.INTEGER);
            }
            ps.setBoolean(13, user.isBlocked);
            ps.setString(14, getUserTypeString(user));
            ps.executeUpdate();

            insertUserRelations(conn, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getUserNumbere() {
        return userNumber;
    }

    public User getUserById(int id) {
        return loadUser(id);
    }

    public static Post getPostById(int id) {
        return loadPost(id);
    }

    public static int getUserNumber() {
        return userNumber++;
    }

    public static void addPost(Post post) {
        try (Connection conn = getConfiguredConnection()) {
            Integer fileId = null;
            if (post.file != null) {
                fileId = insertFile(conn, post.file);
            }
            String sql = "INSERT INTO posts (id, post_text, writer_id, father_post_id, file_id, made_date, saw_number, liked_number, is_blocked, is_deleted, comment_number, retweet_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, post.postId);
            ps.setString(2, post.postText);
            ps.setInt(3, post.writterId);
            if (post.fathersPostId != 0) ps.setInt(4, post.fathersPostId); else ps.setNull(4, Types.INTEGER);
            if (fileId != null) ps.setInt(5, fileId); else ps.setNull(5, Types.INTEGER);
            ps.setTimestamp(6, new Timestamp(post.madeDate.getTime()));
            ps.setInt(7, post.sawNumber);
            ps.setInt(8, post.likedNumber);
            ps.setBoolean(9, post.isBlocked);
            ps.setBoolean(10, post.isDeleted);
            ps.setInt(11, post.commentNumber);
            ps.setInt(12, post.retweetNumber);
            ps.executeUpdate();

            for (int hid : post.hashtags) {
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO post_hashtags (post_id, hashtag_id) VALUES (?,?)");
                ps2.setInt(1, post.postId);
                ps2.setInt(2, hid);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addHashtags(Hashtag hashtag) {
        try (Connection conn = getConfiguredConnection()) {
            int id = nextHashtagId++;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO hashtags (id, title) VALUES (?,?)");
            ps.setInt(1, id);
            ps.setString(2, hashtag.getHashtagTitle());
            ps.executeUpdate();

            try {
                java.lang.reflect.Field f = Hashtag.class.getDeclaredField("hashtagId");
                f.setAccessible(true);
                f.setInt(hashtag, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hashtagNumber++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPostNumber() {
        return postNumber;
    }

    public static void increasePostNumber() {
        postNumber++;
    }

    public static int getHashtagNumber() {
        return hashtagNumber;
    }

    public static int getReportNumber() {
        return reportNumber;
    }

    public static Hashtag getHashtagsById(int i) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, title FROM hashtags WHERE id = ?");
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Hashtag h = new Hashtag(rs.getString("title"));
                try {
                    java.lang.reflect.Field f = Hashtag.class.getDeclaredField("hashtagId");
                    f.setAccessible(true);
                    f.setInt(h, rs.getInt("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return h;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===================== DATABASE LOADERS =====================

    private static User loadUser(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            String fullName = rs.getString("full_name");
            String phone = rs.getString("phone_number");
            String userName = rs.getString("user_name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Date birthDate = rs.getDate("birth_date");
            Date madeDate = rs.getTimestamp("made_date");
            int initialBalance = rs.getInt("initial_balance");
            int tokenNumber = rs.getInt("token_number");
            String biography = rs.getString("biography");
            boolean isBlocked = rs.getBoolean("is_blocked");
            String userType = rs.getString("user_type");

            File profile = null;
            int fileId = rs.getInt("profile_file_id");
            if (!rs.wasNull()) {
                profile = loadFile(conn, fileId);
            }

            // ----- بارگذاری لیست فالوورها -----
            ArrayList<Integer> followers = new ArrayList<>();
            PreparedStatement psf = conn.prepareStatement("SELECT follower_id FROM user_followers WHERE user_id = ?");
            psf.setInt(1, id);
            ResultSet rsf = psf.executeQuery();
            while (rsf.next()) followers.add(rsf.getInt("follower_id"));

            // ----- بارگذاری لیست فالوینگ‌ها -----
            ArrayList<Integer> following = new ArrayList<>();
            PreparedStatement psg = conn.prepareStatement("SELECT user_id FROM user_followers WHERE follower_id = ?");
            psg.setInt(1, id);
            ResultSet rsg = psg.executeQuery();
            while (rsg.next()) following.add(rsg.getInt("user_id"));

            // ----- بارگذاری لیست لایک‌ها -----
            ArrayList<Integer> likedList = new ArrayList<>();
            PreparedStatement psl = conn.prepareStatement("SELECT post_id FROM user_likes WHERE user_id = ?");
            psl.setInt(1, id);
            ResultSet rsl = psl.executeQuery();
            while (rsl.next()) likedList.add(rsl.getInt("post_id"));

            // ----- بارگذاری لیست هشتگ‌های انتخابی -----
            ArrayList<Integer> choseHashtags = new ArrayList<>();
            PreparedStatement psh = conn.prepareStatement("SELECT hashtag_id FROM user_hashtags WHERE user_id = ?");
            psh.setInt(1, id);
            ResultSet rsh = psh.executeQuery();
            while (rsh.next()) choseHashtags.add(rsh.getInt("hashtag_id"));

            // ----- بارگذاری لیست پست‌های کاربر (جدید) -----
            ArrayList<Integer> postsList = new ArrayList<>();
            PreparedStatement psp = conn.prepareStatement("SELECT id FROM posts WHERE writer_id = ? AND is_deleted = false ORDER BY made_date DESC");
            psp.setInt(1, id);
            ResultSet rsp = psp.executeQuery();
            while (rsp.next()) {
                postsList.add(rsp.getInt("id"));
            }

            User user;
            int oldUserNumber = userNumber;
            userNumber = id;
            try {
                switch (userType) {
                    case "NORMAL":
                        user = new BasicUser(fullName, phone, userName, email, password);
                        user.madeDate = madeDate;
                        user.profile = profile;
                        user.biographi = (biography != null) ? biography : "";
                        user.symbol = "";
                        break;
                    case "BLUE":
                        user = new BlueUser(fullName, phone, userName, email, password,
                                birthDate, id, madeDate, initialBalance, tokenNumber,
                                (biography != null) ? biography : "", new ArrayList<>(), followers,
                                following, likedList, profile, isBlocked);
                        break;
                    case "GOLD":
                        user = new GoldUser(fullName, phone, userName, email, password,
                                birthDate, id, madeDate, initialBalance, tokenNumber,
                                (biography != null) ? biography : "", new ArrayList<>(), followers,
                                following, likedList, profile, isBlocked);
                        break;
                    default:
                        user = new BasicUser(fullName, phone, userName, email, password);
                        user.madeDate = madeDate;
                        user.profile = profile;
                        user.biographi = (biography != null) ? biography : "";
                        user.symbol = "";
                }
            } finally {
                userNumber = oldUserNumber;
            }

            // ----- تنظیم همه فیلدها -----
            user.userId = id;
            user.followers = followers;
            user.following = following;
            user.likedList = likedList;
            user.setUserHashtags(choseHashtags);


            user.posts = postsList;

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Post loadPost(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM posts WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            String postText = rs.getString("post_text");
            int writerId = rs.getInt("writer_id");
            int fatherId = rs.getInt("father_post_id");
            Timestamp madeTs = rs.getTimestamp("made_date");
            int sawNumber = rs.getInt("saw_number");
            int likedNumber = rs.getInt("liked_number");
            boolean isBlocked = rs.getBoolean("is_blocked");
            boolean isDeleted = rs.getBoolean("is_deleted");
            int commentNumber = rs.getInt("comment_number");
            int retweetNumber = rs.getInt("retweet_number");
            File file = null;
            int fileId = rs.getInt("file_id");
            if (!rs.wasNull()) {
                file = loadFile(conn, fileId);
            }

            ArrayList<Integer> hashtagIds = new ArrayList<>();
            PreparedStatement ps2 = conn.prepareStatement("SELECT hashtag_id FROM post_hashtags WHERE post_id = ?");
            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) hashtagIds.add(rs2.getInt("hashtag_id"));

            Post post;
            if (fatherId != 0) {
                post = new Post(postText, writerId, hashtagIds, id, fatherId);
            } else {
                post = new Post(postText, writerId, hashtagIds, id);
            }
            post.file = file;
            post.madeDate = madeTs;
            post.sawNumber = sawNumber;
            post.likedNumber = likedNumber;
            post.isBlocked = isBlocked;
            post.isDeleted = isDeleted;
            post.commentNumber = commentNumber;
            post.retweetNumber = retweetNumber;

            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static File loadFile(Connection conn, int fileId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM files WHERE id = ?");
        ps.setInt(1, fileId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String address = rs.getString("file_address");
            String type = rs.getString("file_type");
            if ("PHOTO".equals(type)) {
                String fmt = rs.getString("photo_format");
                return new Photo(PhotoFormat.valueOf(fmt), address);
            } else if ("VIDEO".equals(type)) {
                String fmt = rs.getString("video_format");
                double time = rs.getDouble("video_time");
                String keifiatStr = rs.getString("video_keifiat");
                Keifiat keifiat = (keifiatStr != null) ? Keifiat.valueOf(keifiatStr) : Keifiat.p360;
                return new Video(VideoFormat.valueOf(fmt), address);
            }
        }
        return null;
    }

    // ======================== RESET ALL TABLES ========================
    public static void resetAllTables() {
        try (Connection conn = getConfiguredConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE user_hashtags");
            stmt.execute("TRUNCATE TABLE user_followers");
            stmt.execute("TRUNCATE TABLE user_likes");
            stmt.execute("TRUNCATE TABLE post_hashtags");
            stmt.execute("TRUNCATE TABLE comments");
            stmt.execute("TRUNCATE TABLE post_report");
            stmt.execute("TRUNCATE TABLE user_report");
            stmt.execute("TRUNCATE TABLE posts");
            stmt.execute("TRUNCATE TABLE users");
            stmt.execute("TRUNCATE TABLE hashtags");
            stmt.execute("TRUNCATE TABLE files");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

            // Reset in-memory counters
            userNumber = 0;
            postNumber = 0;
            hashtagNumber = 0;
            reportNumber = 0;
            nextHashtagId = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======================== POST REPORT METHODS ========================

    public static void addPostReport(PostReport report) {
        try (Connection conn = getConfiguredConnection()) {
            String sql = "INSERT INTO post_report (id, reporter_id, reported_post_id, explanation) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, report.getId());
            ps.setInt(2, report.getReporterId());
            ps.setInt(3, report.getReportedId());
            ps.setString(4, report.getExplanation());
            ps.executeUpdate();
            reportNumber++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PostReport getPostReportById(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM post_report WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int reporterId = rs.getInt("reporter_id");
                int reportedPostId = rs.getInt("reported_post_id");
                String explanation = rs.getString("explanation");
                return new PostReport(reporterId, reportedPostId, id, explanation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<PostReport> getAllPostReports() {
        ArrayList<PostReport> list = new ArrayList<>();
        try (Connection conn = getConfiguredConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM post_report ORDER BY id");
            while (rs.next()) {
                int id = rs.getInt("id");
                int reporterId = rs.getInt("reporter_id");
                int reportedPostId = rs.getInt("reported_post_id");
                String explanation = rs.getString("explanation");
                list.add(new PostReport(reporterId, reportedPostId, id, explanation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PostReport> getPostReportsForPost(int postId) {
        ArrayList<PostReport> list = new ArrayList<>();
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM post_report WHERE reported_post_id = ?");
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int reporterId = rs.getInt("reporter_id");
                String explanation = rs.getString("explanation");
                list.add(new PostReport(reporterId, postId, id, explanation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deletePostReport(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM post_report WHERE id = ?");
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) reportNumber--;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Post getPostReportByIndex(int reportId) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT reported_post_id FROM post_report WHERE id = ?");
            ps1.setInt(1, reportId);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                int postId = rs1.getInt("reported_post_id");
                return loadPost(postId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ======================== USER REPORT METHODS ========================

    public static void addUserReport(UserReport report) {
        try (Connection conn = getConfiguredConnection()) {
            String sql = "INSERT INTO user_report (id, reporter_id, reported_user_id, explanation) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, report.getId());
            ps.setInt(2, report.getReporterId());
            ps.setInt(3, report.getReportedId());
            ps.setString(4, report.getExplanation());
            ps.executeUpdate();
            reportNumber++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserReport getUserReportById(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_report WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int reporterId = rs.getInt("reporter_id");
                int reportedUserId = rs.getInt("reported_user_id");
                String explanation = rs.getString("explanation");
                return new UserReport(reporterId, reportedUserId, id, explanation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<UserReport> getAllUserReports() {
        ArrayList<UserReport> list = new ArrayList<>();
        try (Connection conn = getConfiguredConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_report ORDER BY id");
            while (rs.next()) {
                int id = rs.getInt("id");
                int reporterId = rs.getInt("reporter_id");
                int reportedUserId = rs.getInt("reported_user_id");
                String explanation = rs.getString("explanation");
                list.add(new UserReport(reporterId, reportedUserId, id, explanation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<UserReport> getUserReportsForUser(int userId) {
        ArrayList<UserReport> list = new ArrayList<>();
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_report WHERE reported_user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int reporterId = rs.getInt("reporter_id");
                String explanation = rs.getString("explanation");
                list.add(new UserReport(reporterId, userId, id, explanation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteUserReport(int id) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user_report WHERE id = ?");
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) reportNumber--;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUserFromUserReport(int reportId) {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT reported_user_id FROM user_report WHERE id = ?");
            ps1.setInt(1, reportId);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                int userId = rs1.getInt("reported_user_id");
                return loadUser(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPostReportsNumber() {
        try (Connection conn = getConfiguredConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM post_report");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Hashtag getHashtagByIndex(int i){return getHashtagsById(i);}
    public static Post getPostByIndex(int i){return getPostById(i);}


    // ======================== FOLLOW/UNFOLLOW METHODS (SEPARATED) ========================

    /**
     * متد 1: اضافه کردن فالوور به یک کاربر
     * این متد فقط یک رکورد به جدول user_followers اضافه می‌کند
     * @param userId شناسه کاربری که فالوور جدید دریافت می‌کند
     * @param followerId شناسه کاربری که به عنوان فالوور اضافه می‌شود
     * @return true اگر عملیات موفق بود، false در غیر این صورت
     */
    /***public static boolean addFollowerToUser(int userId, int followerId) {
        // بررسی اینکه کاربر خودش را فالو نکند
        if (userId == followerId) {
            System.out.println("Error: A user cannot follow themselves!");
            return false;
        }

        // بررسی اینکه آیا این رابطه از قبل وجود دارد
        try (Connection conn = getConfiguredConnection()) {
            String checkSql = "SELECT COUNT(*) FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, userId);
            checkPs.setInt(2, followerId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Error: This follower already exists!");
                return false;
            }

            // اضافه کردن فالوور جدید
            String sql = "INSERT INTO user_followers (user_id, follower_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);      // کاربری که فالوور می‌گیرد
            ps.setInt(2, followerId);  // کاربری که به عنوان فالوور اضافه می‌شود
            int result = ps.executeUpdate();

            if (result > 0) {
                // به‌روزرسانی لیست فالوورهای کاربر در حافظه (اختیاری)
                User user = loadUser(userId);
                if (user != null && !user.followers.contains(followerId)) {
                    user.followers.add(followerId);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean addFollowingToUser(int userId, int followingId) {
        // بررسی اینکه کاربر خودش را فالو نکند
        if (userId == followingId) {
            System.out.println("Error: A user cannot follow themselves!");
            return false;
        }

        // بررسی اینکه آیا این رابطه از قبل وجود دارد
        try (Connection conn = getConfiguredConnection()) {
            String checkSql = "SELECT COUNT(*) FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, followingId);  // توجه: اینجا برعکس است
            checkPs.setInt(2, userId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Error: This following already exists!");
                return false;
            }


            String sql = "INSERT INTO user_followers (user_id, follower_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, followingId);
            ps.setInt(2, userId);
            int result = ps.executeUpdate();

            if (result > 0) {

                User user = loadUser(userId);
                if (user != null && !user.following.contains(followingId)) {
                    user.following.add(followingId);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean removeFollowerFromUser(int userId, int followerId) {
        // بررسی اینکه آیا این رابطه وجود دارد
        try (Connection conn = getConfiguredConnection()) {
            String checkSql = "SELECT COUNT(*) FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, userId);
            checkPs.setInt(2, followerId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Error: This follower does not exist!");
                return false;
            }

            // حذف فالوور
            String sql = "DELETE FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, followerId);
            int result = ps.executeUpdate();

            if (result > 0) {
                // به‌روزرسانی لیست فالوورهای کاربر در حافظه (اختیاری)
                User user = loadUser(userId);
                if (user != null) {
                    user.followers.remove(Integer.valueOf(followerId));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean removeFollowingFromUser(int userId, int followingId) {
        // بررسی اینکه آیا این رابطه وجود دارد
        try (Connection conn = getConfiguredConnection()) {
            String checkSql = "SELECT COUNT(*) FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, followingId);  // توجه: اینجا برعکس است
            checkPs.setInt(2, userId);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Error: This following does not exist!");
                return false;
            }

            // حذف فالوینگ
            String sql = "DELETE FROM user_followers WHERE user_id = ? AND follower_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, followingId);
            ps.setInt(2, userId);
            int result = ps.executeUpdate();

            if (result > 0) {

                User user = loadUser(userId);
                if (user != null) {
                    user.following.remove(Integer.valueOf(followingId));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}***/