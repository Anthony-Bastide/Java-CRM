package com.example.crm;

import BDD.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import static java.lang.Integer.parseInt;

public class CardUserController {

    private int id;

    @FXML
    private TextField modify_name;
    @FXML
    private TextField modify_identifier;
    @FXML
    private TextField modify_email;
    @FXML
    private TextField modify_password;
    @FXML
    private TextField modify_password2;
    @FXML
    private ChoiceBox<String> modify_role;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    public void getUsersBdd(int id) throws SQLException {
        Users users = new Users();
        ResultSet rs = users.getUsersById(id);

        Preferences prefs = Preferences.userNodeForPackage(Users.class);
        String myRole = prefs.get("role","default_value");
        System.out.println(myRole);
        if (myRole.equals("0")) {
            this.modify_role.setDisable(false);
        } else {
            this.modify_role.setDisable(true);
        }

        this.modify_role.getItems().clear();
        this.modify_role.getItems().add("Admin");
        this.modify_role.getItems().add("User");

        while (rs.next()) {
            modify_name.setText(rs.getString("name"));
            modify_identifier.setText(rs.getString("identifier"));
            modify_email.setText(rs.getString("email"));
            switch (rs.getInt("role")) {
                case 0:
                    this.modify_role.getSelectionModel().select("Admin");
                    break;
                case 1:
                    this.modify_role.getSelectionModel().select("User");
                    break;
                default:
                    this.modify_role.getSelectionModel().select("User");
                    break;
            }
        }
    }

    @FXML
    public void OnClickUpdateInfoUsersBdd() {
        String name = modify_name.getText();
        String identifier = modify_identifier.getText();
        String email = modify_email.getText();
        String role = modify_role.getValue();

        switch (role) {
            case "Admin":
                role = "0";
                break;
            case "User":
                role = "1";
                break;
            default:
                role = "2";
                break;
        }

        Users users = new Users();
        users.updateInfoUser(this.id, name, identifier, email, parseInt(role));
    }

    @FXML
    public void OnClickUpdatePasswordUsersBdd() {
        String password = modify_password.getText();
        String password2 = modify_password2.getText();
        if (password.equals(password2)) {
            Users users = new Users();
            users.updatePasswordUser(this.id, password);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problème de modification du mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez rentrer deux mots de passe identiques, pour pouvoir modifier le mot de passe");
            alert.showAndWait();
        }
    }

    @FXML
    public void OnClickReturn() {
        Preferences prefs = Preferences.userNodeForPackage(Users.class);
        String myRole = prefs.get("role","default_value");
        if (myRole.equals("0")) {
            MainView view = new MainView();
            view.changeSceneForManagementUsers("view_management_users.fxml");
        } else {
            MainView view = new MainView();
            view.changeSceneForIndex("view_index.fxml");
        }
    }
}
