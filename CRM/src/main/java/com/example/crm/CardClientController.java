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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import BDD.Comments;

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
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView<ObservableList<Object>> tableComments;
    @FXML
    private TableColumn<ObservableList<Object>, String> idColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> dateColumn;
    @FXML
    private TableColumn<ObservableList<Object>, String> commentColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> viewActionColumn;
    @FXML
    private TableColumn<ObservableList<Object>, Void> deleteActionColumn;

    private ObservableList<ObservableList<Object>> commentsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));

        viewActionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button();

            {
                FontIcon icon = new FontIcon(FontAwesomeRegular.CARET_SQUARE_RIGHT);

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
        MainView.changeSceneForModifyComment("view_modify_comment.fxml", Integer.parseInt(rowData.get(0).toString()), this.id);
    }

    private void handleDeleteAction(ObservableList<Object> rowData) throws SQLException {
        Comments comments = new Comments();
        boolean success = comments.delete_comment(Integer.parseInt(rowData.get(0).toString()));

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Le commentaire a été supprimé avec succès.");
            alert.showAndWait();
            oneClickSearchComment();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la suppression du commentaire.");
            alert.showAndWait();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    public void getClientsBdd() throws SQLException {
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

        Comments comments = new Comments();
        ResultSet rs2 = comments.getCommentByIDClient(this.id);

        commentsData.clear();

        while (rs2.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(rs2.getInt("id"));
            row.add(rs2.getString("comment"));
            row.add(rs2.getString("date"));
            commentsData.add(row);
        }

        tableComments.setItems(commentsData);

        rs2.close();
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

        boolean success = client.updateClientCard1(this.id, name, country, address, phone, email, civility, info_add, activity);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification du client");
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a bien été modifié");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification du client");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue");
            alert.showAndWait();
        }

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

        boolean success = client.updateClientCard2(this.id, company_name, siret, status, company_activity, company_address, website);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification du client");
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a bien été modifié");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification du client");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue");
            alert.showAndWait();
        }
    }

    @FXML
    private void oneClickReturn() {
        MainView view = new MainView();
        view.changeSceneForIndex("view_index.fxml");
    }

    @FXML
    private void oneClickAddComment(){
        MainView view = new MainView();
        view.changeSceneForAddComment("view_add_comment.fxml",  this.id);
    }

    @FXML
    private void oneClickSearchComment() throws SQLException {
        LocalDate startValue = startDate.getValue();
        LocalDate endValue = endDate.getValue();

        Comments comments = new Comments();

        ResultSet rs = null;

        if (startValue == null || endValue == null ) {
            rs = comments.getCommentByID(this.id);
        } else{
            Date startDate = Date.valueOf(startValue);
            Date endDate = Date.valueOf(endValue);

            rs = comments.searchCommentByDate(this.id, startDate, endDate);
        }

        commentsData.clear();
        while (rs.next()) {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(rs.getInt("id"));
            row.add(rs.getString("comment"));
            row.add(rs.getString("date"));
            commentsData.add(row);
        }

        tableComments.setItems(commentsData);
    }
}
