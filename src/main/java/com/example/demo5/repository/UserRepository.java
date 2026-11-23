package com.example.demo5.repository;

import com.example.demo5.exceptions.CannotFollowYourselfException;
import com.example.demo5.exceptions.UserNotFoundException;
import com.example.demo5.exceptions.WrongPasswordException;
import com.example.demo5.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<User> {

    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO users (full_name, phone_number, user_name, email, password, " +
                "birth_date, made_date, initial_balance, token_number, biography, user_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setDate(6, user.getBirthDate() != null ? new java.sql.Date(user.getBirthDate().getTime()) : null);
            ps.setTimestamp(7, new Timestamp(user.getMadeDate().getTime()));
            ps.setInt(8, user.getInitialBalance());
            ps.setInt(9, user.getTokenNumber());
            ps.setString(10, user.getBiographi());
            ps.setString(11, getUserType(user));

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM users WHERE id = ?";
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
    public boolean update(User user) {
        String sql = "UPDATE users SET full_name=?, phone_number=?, user_name=?, email=?, password=?, " +
                "birth_date=?, biography=?, initial_balance=?, token_number=?, user_type=?, is_blocked=? WHERE id=?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setDate(6, user.getBirthDate() != null ? new java.sql.Date(user.getBirthDate().getTime()) : null);
            ps.setString(7, user.getBiographi());
            ps.setInt(8, user.getInitialBalance());
            ps.setInt(9, user.getTokenNumber());
            ps.setString(10, getUserType(user));
            ps.setBoolean(11, user.isBlocked());
            ps.setInt(12, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // ===================== متدهای اختصاصی =====================

    public User findByUsername(String username) throws UserNotFoundException {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new UserNotFoundException("User not found: " + username);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User login(String usernameOrEmail, String password) throws UserNotFoundException, WrongPasswordException {
        User user = null;
        try {
            user = findByUsername(usernameOrEmail);
        } catch (UserNotFoundException e) {
            user = findByEmail(usernameOrEmail);
        }

        if (user == null) {
            throw new UserNotFoundException("Username/Email not found!");
        }

        if (!user.getPassword().equals(password)) {
            throw new WrongPasswordException("Incorrect password!");
        }

        return user;
    }

    public boolean isEmailExists(String email) {
        return findByEmail(email) != null;
    }

    public boolean isUsernameExists(String username) {
        try {
            findByUsername(username);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    // ===================== متدهای فالو (بدون made_date) =====================

    public boolean followUser(int followerId, int followingId) throws CannotFollowYourselfException{
        if(followerId == followingId){throw new CannotFollowYourselfException("cant follow yourself");
        }



        String sql = "INSERT INTO follows (follower_id, following_id) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfollowUser(int followerId, int followingId) {
        String sql = "DELETE FROM follows WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFollowing(int followerId, int followingId) {
        String sql = "SELECT COUNT(*) FROM follows WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String getUserType(User user) {
        if (user instanceof BlueUser) return "BLUE";
        if (user instanceof GoldUser) return "GOLD";
        return "NORMAL";
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String fullName = rs.getString("full_name");
        String phone = rs.getString("phone_number");
        String username = rs.getString("user_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String type = rs.getString("user_type");

        User user;
        switch (type) {
            case "BLUE":
                user = new BlueUser(fullName, phone, username, email, password);
                break;
            case "GOLD":
                user = new GoldUser(fullName, phone, username, email, password);
                break;
            default:
                user = new BasicUser(fullName, phone, username, email, password);
                break;
        }

        user.setUserId(id);
        user.setBiographi(rs.getString("biography"));
        user.setInitialBalance(rs.getInt("initial_balance"));
        user.setTokenNumber(rs.getInt("token_number"));
        user.setBirthDate(rs.getDate("birth_date"));
        user.setMadeDate(rs.getTimestamp("made_date"));
        user.setBlocked(rs.getBoolean("is_blocked"));

        Connection conn = rs.getStatement().getConnection();

        // 1. لیست پست‌ها
        ArrayList<Integer> posts = new ArrayList<>();
        String postsSql = "SELECT id FROM posts WHERE writer_id = ? AND is_deleted = false ORDER BY made_date DESC";
        try (PreparedStatement ps = conn.prepareStatement(postsSql)) {
            ps.setInt(1, id);
            ResultSet rsPosts = ps.executeQuery();
            while (rsPosts.next()) {
                posts.add(rsPosts.getInt("id"));
            }
        }
        user.setPosts(posts);

        // 2. لیست فالوورها (کسانی که این کاربر رو فالو کردن)
        ArrayList<Integer> followers = new ArrayList<>();
        String followersSql = "SELECT follower_id FROM follows WHERE following_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(followersSql)) {
            ps.setInt(1, id);
            ResultSet rsFollowers = ps.executeQuery();
            while (rsFollowers.next()) {
                followers.add(rsFollowers.getInt("follower_id"));
            }
        }
        user.setFollowers(followers);

        // 3. لیست فالوینگ‌ها (کسانی که این کاربر فالو کرده)
        ArrayList<Integer> following = new ArrayList<>();
        String followingSql = "SELECT following_id FROM follows WHERE follower_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(followingSql)) {
            ps.setInt(1, id);
            ResultSet rsFollowing = ps.executeQuery();
            while (rsFollowing.next()) {
                following.add(rsFollowing.getInt("following_id"));
            }
        }
        user.setFollowing(following);

        // 4. لیست لایک‌ها
        ArrayList<Integer> likedList = new ArrayList<>();
        try {
            String likedSql = "SELECT post_id FROM user_likes WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(likedSql)) {
                ps.setInt(1, id);
                ResultSet rsLiked = ps.executeQuery();
                while (rsLiked.next()) {
                    likedList.add(rsLiked.getInt("post_id"));
                }
            }
        } catch (SQLException e) {
            // اگر جدول user_likes وجود نداشت، خطا رو نادیده بگیر
            System.out.println("Table user_likes not found, skipping likes");
        }
        user.setLikedList(likedList);

        return user;
    }
}