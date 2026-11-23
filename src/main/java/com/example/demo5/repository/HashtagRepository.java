package com.example.demo5.repository;

import com.example.demo5.models.Hashtag;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HashtagRepository implements IRepository<Hashtag> {

    @Override
    public boolean add(Hashtag hashtag) {
        String sql = "INSERT INTO hashtags (hashtag_text) VALUES (?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hashtag.getHashtagTitle());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hashtag.setHashtagId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM hashtags WHERE id = ?";
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
    public boolean update(Hashtag hashtag) {
        String sql = "UPDATE hashtags SET hashtag_text = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashtag.getHashtagTitle());
            ps.setInt(2, hashtag.getHashtagId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Hashtag findById(int id) {
        String sql = "SELECT * FROM hashtags WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToHashtag(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hashtag> findAll() {
        List<Hashtag> hashtags = new ArrayList<>();
        String sql = "SELECT * FROM hashtags ORDER BY hashtag_text";
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                hashtags.add(mapResultSetToHashtag(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashtags;
    }


    public Hashtag findByText(String text) {
        String sql = "SELECT * FROM hashtags WHERE hashtag_text = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, text);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToHashtag(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hashtag> getPopularHashtags(int limit) {
        List<Hashtag> hashtags = new ArrayList<>();
        String sql = "SELECT h.*, COUNT(ph.post_id) as post_count FROM hashtags h " +
                "JOIN post_hashtags ph ON h.id = ph.hashtag_id " +
                "GROUP BY h.id ORDER BY post_count DESC LIMIT ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hashtags.add(mapResultSetToHashtag(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashtags;
    }

    public void addUserHashtag(int userId, int hashtagId) {
        String sql = "INSERT INTO user_hashtags (user_id, hashtag_id) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, hashtagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserHashtag(int userId, int hashtagId) {
        String sql = "DELETE FROM user_hashtags WHERE user_id = ? AND hashtag_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, hashtagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Hashtag mapResultSetToHashtag(ResultSet rs) throws SQLException {
        Hashtag hashtag = new Hashtag(rs.getString("hashtag_text"), rs.getInt("id"));

        return hashtag;
    }
}