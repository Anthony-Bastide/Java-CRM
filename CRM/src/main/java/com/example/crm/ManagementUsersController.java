package com.example.crm;

import BDD.Users;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementUsersController {
    private String name;
    private String identifier;
    private String email;
    private String role;

    @FXML
    private TextField search_name;
    @FXML
    private TextField search_identifier;
    @FXML
    private TextField search_email;
    @FXML
    private ChoiceBox<String> search_role;
    @FXML
    private TableView<ObservableList<Object>> tableUsers;
    @FXML
    private TableColumn<ObservableList<Object>, String> idColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> nameColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> identifierColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> emailColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> roleColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> viewActionColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> deleteActionColumn;

    private ObservableList<ObservableList<Object>> usersData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        identifierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));

        viewActionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button();

            {
                FontIcon icon = new FontIcon(FontAwesomeRegular.USER);

                icon.setIconSize(16);

                viewButton.setGraphic(icon);

                viewButton.setOnAction(event -> {
                    ObservableList<Object> rowData = getTableView().getItems().get(getIndex());
                    handleViewAction(rowData);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });

        deleteActionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                FontIcon icon = new FontIcon(FontAwesomeSolid.TRASH);

                icon.setIconSize(16);

                deleteButton.setGraphic(icon);

                deleteButton.setOnAction(event -> {
                    ObservableList<Object> rowData = getTableView().getItems().get(getIndex());
                    try {
                        handleDeleteAction(rowData);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void handleViewAction(ObservableList<Object> rowData) {
        MainView.changeSceneForFicheUser("view_user_profil.fxml", Integer.parseInt(rowData.get(0).toString()));
    }

    private void handleDeleteAction(ObservableList<Object> rowData) throws SQLException {
        Users users = new Users();
        boolean success = users.deleteUser(Integer.parseInt(rowData.get(0).toString()));

        if (success) {
            oneClickSearch();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Une erreur est survenue");
            alert.showAndWait();
        }
    }

    public void setSearch(String name, String identifier, String email, String role) {
        this.name = name;
        this.identifier = identifier;
        this.email = email;
        this.role = role;
    }

    @FXML
    public void getUsersBdd() throws SQLException {
        Users users = new Users();
        ResultSet rs = users.getUsers();

        this.search_role.getItems().clear();
        this.search_role.getItems().add("Tous");
        this.search_role.getSelectionModel().select("Tous");
        this.search_role.getItems().add("Admin");
        this.search_role.getItems().add("User");

        usersData.clear();
        while (rs.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(rs.getInt("id"));
            row.add(rs.getString("name"));
            row.add(rs.getString("identifier"));
            row.add(rs.getString("email"));
            if (rs.getInt("role") == 0) {
                row.add("Admin");
            } else {
                row.add("User");
            }
            usersData.add(row);
        }

        tableUsers.setItems(usersData);

    }

    @FXML
    private void oneClickSearch() throws SQLException {
        String name = search_name.getText();
        String identifier = search_identifier.getText();
        String email = search_email.getText();
        String role = search_role.getValue();

        switch (role) {
            case "Admin":
                role = "0";
                break;
            case "User":
                role = "1";
                break;
            default:
                role = "";
                break;
        }

        Users users = new Users();
        ResultSet rs = users.getUsersByFilter(name, identifier, email, role);

        usersData.clear();
        while (rs.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(rs.getInt("id"));
            row.add(rs.getString("name"));
            row.add(rs.getString("identifier"));
            row.add(rs.getString("email"));
            if (rs.getInt("role") == 0) {
                row.add("Admin");
            } else {
                row.add("User");
            }
            usersData.add(row);
        }

        tableUsers.setItems(usersData);

    }

    @FXML
    public void OnClickReturn() {
        MainView view = new MainView();
        view.changeSceneForIndex("view_index.fxml");
    }

    @FXML
    public void OnClickAddUser() {
        MainView view = new MainView();
        view.changeSceneForAddUser("view_add_user.fxml");
    }
}
