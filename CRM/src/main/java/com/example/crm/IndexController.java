package com.example.crm;

import BDD.Users;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class IndexController {
    @FXML
    private TextField search_name;
    @FXML
    private TextField search_address;
    @FXML
    private TextField search_country;
    @FXML
    private TextField search_email;

    @FXML
    private void oneClickSearch() {

        String name = this.search_name.getText();
        String address = this.search_address.getText();
        String country = this.search_country.getText();
        String email = this.search_email.getText();

        MainView view = new MainView();
        view.changeSceneForSearch(name, country, email, address, "view_search.fxml");
    }
    @FXML
    private void oneClickAdd() {

        MainView view = new MainView();
        view.changeSceneForAdd("view_add.fxml");
    }
    @FXML
    private void oneClickManagementUsers() {
        Preferences prefs = Preferences.userNodeForPackage(Users.class);
        String myRole = prefs.get("role","default_value");
        if (myRole.equals("0")) {
            MainView view = new MainView();
            view.changeSceneForManagementUsers("view_management_users.fxml");
        } else {
            String myId = prefs.get("id","default_value");
            MainView.changeSceneForFicheUser("view_user_profil.fxml", Integer.parseInt(myId));
        }
    }
}
