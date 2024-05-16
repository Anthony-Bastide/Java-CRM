package com.example.crm;

import BDD.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;
import java.util.prefs.Preferences;

public class LoginController {

    @FXML
    private TextField identifier;
    @FXML
    private PasswordField password;

    @FXML
    private void oneClickLogin() throws NoSuchAlgorithmException {
        Users user = new Users();
        if (user.login(identifier.getText(), password.getText())) {
            MainView view = new MainView();
            int id = user.getIdByLogin(identifier.getText(), password.getText());
            int role = user.getRoleById(id);
            Preferences prefs = Preferences.userNodeForPackage(Users.class);
            prefs.put("id", String.valueOf(id));
            prefs.put("role", String.valueOf(role));
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
