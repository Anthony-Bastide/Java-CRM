package com.example.crm;

import BDD.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import static java.lang.Integer.parseInt;

public class AddUserController {
    @FXML
    private TextField add_name;
    @FXML
    private TextField add_identifier;
    @FXML
    private TextField add_email;
    @FXML
    private TextField add_password;
    @FXML
    private TextField add_password2;
    @FXML
    private ChoiceBox<String> add_role;

    public void addDisplayChoiceBox() {
        add_role.getItems().clear();
        add_role.getItems().add("Admin");
        add_role.getItems().add("User");
    }

    @FXML
    public void addOnClickAddUsersBdd() {
        Users user = new Users();
        String roleChoice = add_role.getValue();
        int role = 1;
        if (add_password.getText().equals(add_password2.getText())) {
            switch (roleChoice) {
                case "Admin":
                    role = 0;
                    break;
                case "User":
                    role = 1;
                    break;
                default:
                    role = 1;
                    break;
            }

            user.addUser(add_name.getText(), add_identifier.getText(), add_email.getText(), add_password.getText(), role);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problème d'authentification");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne sont pas identiques");
            alert.showAndWait();
        }
    }

    @FXML
    public void OnClickReturn() {
        MainView view = new MainView();
        view.changeSceneForManagementUsers("view_management_users.fxml");
    }
}
