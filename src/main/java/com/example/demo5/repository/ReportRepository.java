package com.example.demo5.repository;

import com.example.demo5.models.PostReport;
import com.example.demo5.models.Report;
import com.example.demo5.models.Situation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository implements IRepository<Report> {

    @Override
    public boolean add(Report report) {
        String sql = "INSERT INTO reports (reporter_user_id, reported_post_id, reason, made_date) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, report.getReporterId());
            ps.setInt(2, report.getReportedId());
            ps.setString(3, report.getExplanation());
            ps.setTimestamp(4, new Timestamp(report.getMadeDate().getTime()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        report.setReportId(generatedKeys.getInt(1));
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

    public List<PostReport> findAllPostReports() {
        List<PostReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports ORDER BY made_date DESC";
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                reports.add(mapResultSetToPostReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    private PostReport mapResultSetToPostReport(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int reporterId = rs.getInt("reporter_user_id");
        int reportedPostId = rs.getInt("reported_post_id");
        String reason = rs.getString("reason");
        // وضعیت در دیتابیس ذخیره نمی‌شود، پیش‌فرض WAITING
        return new PostReport(reporterId, reportedPostId, id, reason, Situation.WAITING);
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM reports WHERE id = ?";
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
    public boolean update(Report report) {
        String sql = "UPDATE reports SET reason = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, report.getExplanation());
            ps.setInt(2, report.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Report findById(int id) {
        String sql = "SELECT * FROM reports WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToReport(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports ORDER BY made_date DESC";
        try (Connection conn = MySQLConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }


    public List<Report> getReportsForPost(int postId) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE reported_post_id = ? ORDER BY made_date DESC";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getReportsByUser(int userId) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE reporter_user_id = ? ORDER BY made_date DESC";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(mapResultSetToReport(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public boolean hasUserReportedPost(int userId, int postId) {
        String sql = "SELECT COUNT(*) FROM reports WHERE reporter_user_id = ? AND reported_post_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Report mapResultSetToReport(ResultSet rs) throws SQLException {
        Report report = new Report(rs.getInt("reporter_user_id"), rs.getInt("reported_post_id"), rs.getInt("id"), (rs.getString("reason")), Situation.WAITING);

        return report;
    }
}