package BDD;

import java.sql.*;
import java.util.prefs.Preferences;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Users {
    public boolean login(String identifier, String password) throws NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        password = hexString.toString();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM users WHERE identifier = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, identifier);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int role = rs.getInt("role");
                Preferences prefs = Preferences.userNodeForPackage(Users.class);
                prefs.put("id", String.valueOf(id));
                prefs.put("role", String.valueOf(role));

                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    public int getIdByLogin(String identifier, String password) throws NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        password = hexString.toString();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT id FROM users WHERE identifier = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, identifier);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    public int getRoleById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT role FROM users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public ResultSet getUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM users";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getUsersByFilter(String name, String identifier, String email, String role) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM users WHERE name LIKE ? AND identifier LIKE ? AND email LIKE ? AND role LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + name + "%");
            stmt.setString(2, "%" + identifier + "%");
            stmt.setString(3, "%" + email + "%");
            stmt.setString(4, "%" + role + "%");
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "DELETE FROM users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getUsersById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM users WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateInfoUser(int id, String name, String identifier, String email, int role) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "UPDATE users SET name = ?, identifier = ?, email = ?, role = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, identifier);
            stmt.setString(3, email);
            stmt.setInt(4, role);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean updatePasswordUser(int id, String password) throws NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        password = hexString.toString();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "UPDATE users SET password = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean addUser(String name, String identifier, String email, String password, int addRole) throws NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement stmt = null;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        password = hexString.toString();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "INSERT INTO users (name, identifier, email, password, role) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, identifier);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setInt(5, addRole);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
