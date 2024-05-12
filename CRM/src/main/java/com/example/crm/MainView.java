package com.example.crm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainView extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("view_login.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void changeSceneForFicheClient(String fxml, int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            scene.setRoot(root);

            Platform.runLater(() -> {
                CardClientController cardClient = fxmlLoader.getController();
                cardClient.setId(id);
                try {
                    cardClient.getClientsBdd();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSceneForSearch(String name, String country, String email, String address, String fxml) {
        System.out.println("changeSceneForSearch");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            scene.setRoot(root);

            Platform.runLater(() -> {
                SearchController search = fxmlLoader.getController();
                search.setSearch(name, country, email, address);
                try {
                    search.getClientsBdd();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeSceneForAdd(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSceneForIndex(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
