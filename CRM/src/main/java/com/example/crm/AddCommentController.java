package com.example.crm;

import BDD.Comments;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.sql.Date;

public class AddCommentController {
    int id;

    @FXML
    private DatePicker add_date;
    @FXML
    private TextArea add_comment;

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    private void oneClickAddComment(){
        String comment = add_comment.getText();
        Date date = Date.valueOf(add_date.getValue());
        Comments comments = new Comments();
        boolean success = comments.add_comment(this.id,date, comment);
        if (success){
            MainView.changeSceneForFicheClient("view_card_client.fxml", this.id);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Probleme d'ajout");
            alert.setHeaderText(null);
            alert.setContentText("Le commentaire n'a pas pu etre ajouter");
            alert.showAndWait();
        }
    }

    @FXML
    private void oneClickReturn(){
        MainView.changeSceneForFicheClient("view_card_client.fxml", this.id);
    }
}
