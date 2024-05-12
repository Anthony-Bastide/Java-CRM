package com.example.crm;

import BDD.Clients;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddController {
    @FXML
    private TextField add_name;
    @FXML
    private TextField add_address;
    @FXML
    private TextField add_country;
    @FXML
    private TextField add_email;

    @FXML
    protected void oneClickAdd() {

        String name = this.add_name.getText();
        String address = this.add_address.getText();
        String country = this.add_country.getText();
        String email = this.add_email.getText();

        Clients clients = new Clients();
        int id_client = clients.add_client(name, country, email, address);

        MainView view = new MainView();
        MainView.changeSceneForFicheClient("view_card_client.fxml", id_client);
    }
    @FXML
    private void oneClickReturn() {
        MainView view = new MainView();
        view.changeSceneForIndex("view_index.fxml");
    }

}
