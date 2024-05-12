package com.example.crm;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
}
