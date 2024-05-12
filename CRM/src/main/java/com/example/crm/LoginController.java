package com.example.crm;

import BDD.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField identifier;
    @FXML
    private TextField password;

    @FXML
    private void oneClickLogin() {
        System.out.println("fdf");
        Users users = new Users();
        if (users.login(identifier.getText(), password.getText())) {
            MainView view = new MainView();
            view.changeSceneForIndex("view_index.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problème d'authentification");
            alert.setHeaderText(null);
            alert.setContentText("Identifiant ou mot de passe incorrect, vérifiez que vous avez saisi le bon nom d'utilisateur et mot de passe pour ce compte");
            alert.showAndWait();
        }
    }
}
