package BDD;

import java.sql.*;

public class Users {
    public boolean login(String identifier, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM users WHERE identifier = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, identifier);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
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
}
