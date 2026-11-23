package com.example.demo5.repository;

import com.example.demo5.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository implements IRepository<Post> {

    @Override
    public boolean add(Post post) {
        String sql = "INSERT INTO posts (post_text, writer_id, father_post_id, file_id, " +
                "made_date, saw_number, liked_number, is_blocked, is_deleted, " +
                "comment_number, retweet_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, post.getPostText());
            ps.setInt(2, post.getWritterId());

            if (post.getFatherPostId() != 0) {
                ps.setInt(3, post.getFatherPostId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (post.getPostFile() != null) {
                int fileId = saveFile(post.getPostFile());
                ps.setInt(4, fileId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setTimestamp(5, new Timestamp(post.getMadeDate().getTime()));
            ps.setInt(6, post.getSawNumber());
            ps.setInt(7, post.getLikesNumber());
            ps.setBoolean(8, post.isBlocked());
            ps.setBoolean(9, post.isDeleted());
            ps.setInt(10, post.getCommentsNumber());
            ps.setInt(11, post.getRetweetCount());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int postId = generatedKeys.getInt(1);
                        post.setPostId(postId); // تنظیم آی‌دی تولید شده

                        // ذخیره هشتگ‌های پست
                        if (post.getHashtags() != null && !post.getHashtags().isEmpty()) {
                            savePostHashtags(postId, post.getHashtags());
                        }

                        return true;
                    }
                }
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        // Soft delete
        String sql = "UPDATE posts SET is_deleted = true WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Post post) {
        String sql = "UPDATE posts SET post_text=?, saw_number=?, liked_number=?, " +
                "comment_number=?, retweet_number=? WHERE id=? AND is_deleted = false";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, post.getPostText());
            ps.setInt(2, post.getSawNumber());
            ps.setInt(3, post.getLikesNumber());
            ps.setInt(4, post.getCommentsNumber());
            ps.setInt(5, post.getRetweetCount());
            ps.setInt(6, post.getPostId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Post findById(int id) {
        String sql = "SELECT * FROM posts WHERE id = ? AND is_deleted = false";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE is_deleted = false ORDER BY made_date DESC";
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }


    public List<Post> getFeedForUser(int userId) {
        List<Post> feed = new ArrayList<>();

        // ابتدا هشتگ‌های انتخابی کاربر را پیدا می‌کنیم
        String hashtagSql = "SELECT hashtag_id FROM user_hashtags WHERE user_id = ?";
        List<Integer> userHashtags = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(hashtagSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userHashtags.add(rs.getInt("hashtag_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return feed;
        }

        if (userHashtags.isEmpty()) {
            return getRecentPosts(10);
        }

        String inClause = String.join(",", userHashtags.stream()
                .map(String::valueOf)
                .toArray(String[]::new));

        String postSql = "SELECT DISTINCT p.* FROM posts p " +
                "JOIN post_hashtags ph ON p.id = ph.post_id " +
                "WHERE ph.hashtag_id IN (" + inClause + ") " +
                "AND p.is_deleted = false " +
                "ORDER BY p.made_date DESC " +
                "LIMIT 20";

        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(postSql)) {
            while (rs.next()) {
                feed.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // اگر کمتر از ۱۰ پست داشت، پست‌های عمومی را هم اضافه کن
        if (feed.size() < 10) {
            List<Post> generalPosts = getRecentPosts(10 - feed.size());
            for (Post p : generalPosts) {
                if (!isPostInList(feed, p.getPostId())) {
                    feed.add(p);
                }
            }
        }

        return feed;
    }

    public List<Post> getRecentPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE is_deleted = false " +
                "ORDER BY made_date DESC LIMIT ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> getPopularPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE is_deleted = false " +
                "ORDER BY liked_number DESC LIMIT ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> getRepliesForPost(int postId) {
        List<Post> replies = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE father_post_id = ? AND is_deleted = false " +
                "ORDER BY made_date ASC";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                replies.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return replies;
    }

    public void incrementViewCount(int postId) {
        String sql = "UPDATE posts SET saw_number = saw_number + 1 WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incrementLikeCount(int postId) {
        String sql = "UPDATE posts SET liked_number = liked_number + 1 WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decrementLikeCount(int postId) {
        String sql = "UPDATE posts SET liked_number = liked_number - 1 WHERE id = ? AND liked_number > 0";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incrementRetweetCount(int postId) {
        String sql = "UPDATE posts SET retweet_number = retweet_number + 1 WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int saveFile(File file) throws SQLException {
        String sql = "INSERT INTO files (file_address, file_type, photo_format, video_format) VALUES (?, ?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, file.getFileAddress());

            if (file instanceof Photo) {
                ps.setString(2, "PHOTO");
                ps.setString(3, ((Photo) file).getPhotoFormat().name());
                ps.setNull(4, Types.VARCHAR);
            } else if (file instanceof Video) {
                ps.setString(2, "VIDEO");
                ps.setNull(3, Types.VARCHAR);
                ps.setString(4, ((Video) file).getVideoFormat().name());
            } else {
                ps.setString(2, "UNKNOWN");
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
            }

            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            throw new SQLException("Failed to save file - no ID generated");
        }
    }

    public void updateBlockStatus(int postId, boolean blocked) {
        String sql = "UPDATE posts SET is_blocked = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, blocked);
            ps.setInt(2, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePostHashtags(int postId, ArrayList<Integer> hashtags) throws SQLException {
        if (hashtags == null || hashtags.isEmpty()) return;

        String sql = "INSERT INTO post_hashtags (post_id, hashtag_id) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int hashtagId : hashtags) {
                ps.setInt(1, postId);
                ps.setInt(2, hashtagId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String text = rs.getString("post_text");
        int writerId = rs.getInt("writer_id");
        int fatherId = rs.getInt("father_post_id");
        Timestamp madeDate = rs.getTimestamp("made_date");
        int sawNumber = rs.getInt("saw_number");
        int likedNumber = rs.getInt("liked_number");
        boolean isBlocked = rs.getBoolean("is_blocked");
        boolean isDeleted = rs.getBoolean("is_deleted");
        int commentNumber = rs.getInt("comment_number");
        int retweetNumber = rs.getInt("retweet_number");

        ArrayList<Integer> hashtags = getPostHashtags(id);

        File file = null;
        int fileId = rs.getInt("file_id");
        if (!rs.wasNull()) {
            file = loadFile(fileId);
        }

        // دریافت لیست لایک‌ها
        ArrayList<Integer> likesList = new ArrayList<>();
        String likesSql = "SELECT user_id FROM user_likes WHERE post_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(likesSql)) {
            ps.setInt(1, id);
            ResultSet rsLikes = ps.executeQuery();
            while (rsLikes.next()) {
                likesList.add(rsLikes.getInt("user_id"));
            }
        } catch (SQLException e) {
            System.out.println("Table user_likes not found, skipping likes");
        }

        // دریافت لیست پاسخ‌ها (کامنت‌ها)
        ArrayList<Integer> answerPostsList = new ArrayList<>();
        String answerSql = "SELECT id FROM posts WHERE father_post_id = ? AND is_deleted = false ORDER BY made_date ASC";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(answerSql)) {
            ps.setInt(1, id);
            ResultSet rsAnswer = ps.executeQuery();
            while (rsAnswer.next()) {
                answerPostsList.add(rsAnswer.getInt("id"));
            }
        } catch (SQLException e) {
        }

        Post post = new Post(
                fatherId,
                isBlocked,
                null,
                retweetNumber,
                commentNumber,
                isDeleted,
                answerPostsList,
                sawNumber,
                likedNumber,
                id,
                text,
                file,
                writerId,
                madeDate,
                hashtags,
                likesList
        );

        return post;
    }
    private ArrayList<Integer> getPostHashtags(int postId) throws SQLException {
        ArrayList<Integer> hashtags = new ArrayList<>();
        String sql = "SELECT hashtag_id FROM post_hashtags WHERE post_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hashtags.add(rs.getInt("hashtag_id"));
            }
        }
        return hashtags;
    }

    private File loadFile(int fileId) {
        String sql = "SELECT * FROM files WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fileId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String address = rs.getString("file_address");
                String type = rs.getString("file_type");
                if ("PHOTO".equals(type)) {
                    String format = rs.getString("photo_format");
                    return new Photo(PhotoFormat.valueOf(format), address);
                } else if ("VIDEO".equals(type)) {
                    String format = rs.getString("video_format");
                    return new Video(VideoFormat.valueOf(format), address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isPostInList(List<Post> list, int postId) {
        for (Post p : list) {
            if (p.getPostId() == postId) return true;
        }
        return false;
    }

    public void incrementCommentCount(int postId) {
        String sql = "UPDATE posts SET comment_number = comment_number + 1 WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}