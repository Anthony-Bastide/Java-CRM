package BDD;

import java.sql.*;

public class Clients {

    public int add_client(String name, String country, String email, String address) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int clientId = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "INSERT INTO clients (name, country, email, address) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, country);
            stmt.setString(3, email);
            stmt.setString(4, address);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La création du client a échoué, aucune ligne affectée.");
            }
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                clientId = rs.getInt(1);
            } else {
                throw new SQLException("La récupération de l'ID du client a échoué, aucun ID généré.");
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

        return clientId;
    }

    public ResultSet getClientByID(int id) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "SELECT * FROM clients WHERE id ="+id;
            stmt =  conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getClientByFilter(String name, String country, String email, String address) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM clients WHERE id IS NOT NULL");
            if (!name.isEmpty()) {
                queryBuilder.append(" AND name LIKE ?");
            }
            if (!country.isEmpty()) {
                queryBuilder.append(" AND country LIKE ?");
            }
            if (!email.isEmpty()) {
                queryBuilder.append(" AND email LIKE ?");
            }
            if (!address.isEmpty()) {
                queryBuilder.append(" AND address LIKE ?");
            }
            String query = queryBuilder.toString();

            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int paramIndex = 1;
            if (!name.isEmpty()) {
                stmt.setString(paramIndex++, "%" + name + "%");
            }
            if (!country.isEmpty()) {
                stmt.setString(paramIndex++, "%" + country + "%");
            }
            if (!email.isEmpty()) {
                stmt.setString(paramIndex++, "%" + email + "%");
            }
            if (!address.isEmpty()) {
                stmt.setString(paramIndex, "%" + address + "%");
            }

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public void updateClientCard1(int id, String name, String country, String address, String phone, String email,
                                  String civility, String info_add, String activity) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "UPDATE clients SET name = ?, country = ?, address = ?, phone = ?, email = ?, civility = ?, " +
                    "info_add = ?, activity = ? WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, country);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.setString(6, civility);
            stmt.setString(7, info_add);
            stmt.setString(8, activity);
            stmt.setInt(9, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClientCard2(int id, String company_name, String siret, String status, String company_activity,
                                  String company_address, String website) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "UPDATE clients SET company_name = ?, siret = ?, status = ?, company_activity = ?, " +
                    "company_address = ?, website = ? WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, company_name);
            stmt.setString(2, siret);
            stmt.setString(3, status);
            stmt.setString(4, company_activity);
            stmt.setString(5, company_address);
            stmt.setString(6, website);
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_java", "root", "");
            String query = "DELETE FROM clients WHERE id = ?";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
