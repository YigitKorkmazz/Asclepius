package com.example.demo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class Notification extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button notifyButton = new Button("Show Notification");
        notifyButton.setOnAction(event -> showNotification("Urgent Blood Donation", "Donate Blood"));

        VBox root = new VBox(notifyButton);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Notification");
        primaryStage.show();
    }

    public void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

