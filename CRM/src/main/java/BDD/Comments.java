package BDD;

import java.sql.*;
import java.time.LocalDate;

public class Comments {

    public boolean add_comment(int id, Date date, String comment) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "INSERT INTO comments (date, comment, id_client) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, date);
            stmt.setString(2, comment);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete_comment(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "DELETE FROM comments WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet getCommentByID(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM comments WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean modifyComment(int id, Date date, String comment) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "UPDATE comments SET date = ?, comment = ? WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, date);
            stmt.setString(2, comment);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public ResultSet searchCommentByDate(int id, Date startDate, Date endDate) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM comments WHERE id_client = ? AND date BETWEEN ? AND ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getCommentByIDClient(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM comments WHERE id_client = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
