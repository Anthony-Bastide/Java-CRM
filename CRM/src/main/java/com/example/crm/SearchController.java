package com.example.crm;

import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import BDD.Clients;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchController {

    private String name;
    private String country;
    private String email;
    private String address;

    @FXML
    private TextField search_name;
    @FXML
    private TextField search_address;
    @FXML
    private TextField search_country;
    @FXML
    private TextField search_email;
    @FXML
    private TableView<ObservableList<Object>> tableClient;
    @FXML
    private TableColumn<ObservableList<Object>, String> idColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> nameColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> countryColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> emailColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> viewActionColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> deleteActionColumn;

    private ObservableList<ObservableList<Object>> clientsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));

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
        MainView.changeSceneForFicheClient("view_card_client.fxml", Integer.parseInt(rowData.get(0).toString()));
    }

    private void handleDeleteAction(ObservableList<Object> rowData) throws SQLException {
        Clients clients = new Clients();
        clients.deleteClient(Integer.parseInt(rowData.get(0).toString()));
    }

    public void setSearch(String name, String country, String email, String address) {
        this.name = name;
        this.country = country;
        this.email = email;
        this.address = address;
    }

    @FXML
    public void getClientsBdd() throws SQLException {
        this.search_name.setText(this.name);
        this.search_country.setText(this.country);
        this.search_email.setText(this.email);
        this.search_address.setText(this.address);

        Clients clients = new Clients();
        ResultSet rs = clients.getClientByFilter(this.name, this.country, this.email, this.address);

        clientsData.clear();

        while (rs.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(rs.getInt("id"));
            row.add(rs.getString("name"));
            row.add(rs.getString("country"));
            row.add(rs.getString("email"));
            clientsData.add(row);
        }

        tableClient.setItems(clientsData);
    }

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
