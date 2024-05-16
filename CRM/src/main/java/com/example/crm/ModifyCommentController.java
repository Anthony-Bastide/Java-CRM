package com.example.crm;

import BDD.Comments;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyCommentController {
    int id;
    int idClient;

    @FXML
    private DatePicker modify_date;
    @FXML
    private TextArea modify_comment;

    public void setId(int id, int idClient) {

        this.id = id;
        this.idClient = idClient;
    }

    @FXML
    public void getCommentsBdd() throws SQLException {
        Comments comments = new Comments();
        ResultSet rs = comments.getCommentByID(this.id);
        System.out.println(this.id);
        while (rs.next()) {
            System.out.println(rs.getString("comment"));
            modify_date.setValue(rs.getDate("date").toLocalDate());
            modify_comment.setText(rs.getString("comment"));
        }
    }

    @FXML
    private void oneClickModifyComment(){
        String comment = modify_comment.getText();
        Date date = Date.valueOf(modify_date.getValue());

        Comments comments = new Comments();
        boolean result = comments.modifyComment(this.id, date, comment);

        if (result) {
            MainView.changeSceneForFicheClient("view_card_client.fxml", this.idClient);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Probleme de modification");
            alert.setHeaderText(null);
            alert.setContentText("Il y a eu un probleme lors de la modification du commentaire. Veuillez reessayer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void oneClickReturn(){
        MainView.changeSceneForFicheClient("view_card_client.fxml", this.id);
    }
}
