package com.example.crm;

import BDD.Clients;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardClientController {
    private int id;

    @FXML
    private TextField modify_name;
    @FXML
    private TextField modify_country;
    @FXML
    private TextField modify_address;
    @FXML
    private TextField modify_activity;
    @FXML
    private TextField modify_civility;
    @FXML
    private TextField modify_status;
    @FXML
    private TextField modify_company_name;
    @FXML
    private TextField modify_company_address;
    @FXML
    private TextField modify_company_activity;
    @FXML
    private TextField modify_website;
    @FXML
    private TextArea modify_info_add;
    @FXML
    private TextField modify_email;
    @FXML
    private TextField modify_phone;
    @FXML
    private TextField modify_siret;

    public void setId(int id) {
        this.id = id;
    }
    @FXML
    public void getClientsBdd() throws SQLException {
        System.out.println("click");
        Clients client = new Clients();
        ResultSet rs = client.getClientByID(this.id);

        if (rs.next()) {
            modify_name.setText(rs.getString("name"));
            modify_country.setText(rs.getString("country"));
            modify_address.setText(rs.getString("address"));
            modify_phone.setText(rs.getString("phone"));
            modify_email.setText(rs.getString("email"));
            modify_website.setText(rs.getString("website"));
            modify_info_add.setText(rs.getString("info_add"));
            modify_company_name.setText(rs.getString("company_name"));
            modify_siret.setText(rs.getString("siret"));
            modify_activity.setText(rs.getString("activity"));
            modify_status.setText(rs.getString("status"));
            modify_civility.setText(rs.getString("civility"));
            modify_company_activity.setText(rs.getString("company_activity"));
            modify_company_address.setText(rs.getString("company_address"));
        } else {
            System.out.println("Aucun résultat trouvé pour l'ID spécifié.");
        }

        rs.close();
    }

    @FXML
    public void updateClientCard1() throws SQLException {
        Clients client = new Clients();

        String name = modify_name.getText();
        String country = modify_country.getText();
        String address = modify_address.getText();
        String phone = modify_phone.getText();
        String email = modify_email.getText();
        String civility = modify_civility.getText();
        String info_add = modify_info_add.getText();
        String activity = modify_activity.getText();

        client.updateClientCard1(this.id, name, country, address, phone, email, civility, info_add, activity);
        getClientsBdd();

    }
    @FXML
    public void updateClientCard2() throws SQLException {
        Clients client = new Clients();

        String company_name = modify_company_name.getText();
        String siret = modify_siret.getText();
        String status = modify_status.getText();
        String company_activity = modify_company_activity.getText();
        String company_address = modify_company_address.getText();
        String website = modify_website.getText();

        client.updateClientCard2(this.id, company_name, siret, status, company_activity, company_address, website);
        getClientsBdd();
    }
    @FXML
    private void oneClickReturn() {
        MainView view = new MainView();
        view.changeSceneForIndex("view_index.fxml");
    }

}
