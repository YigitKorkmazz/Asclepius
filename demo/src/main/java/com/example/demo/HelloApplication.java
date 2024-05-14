package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class HelloApplication extends Application {

public void start(Stage stage) throws IOException {

        Image logoImage = new Image("file:demo/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), logoImageView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);

        StackPane root = new StackPane();
        root.getChildren().add(logoImageView);

        Scene scene = new Scene(root, 600, 675, Color.RED);
        stage.setScene(scene);
        stage.setTitle("Logo Animation");

        fadeTransition.play();
        stage.show();
        fadeTransition.setOnFinished(event -> {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                        Scene newScene = new Scene(fxmlLoader.load(), 600, 675);
                        stage.setTitle("Hello!");
                        stage.setScene(newScene);
                } catch (IOException e) {
                        e.printStackTrace(); // Handle the exception as appropriate
                }

        });
        }

public static void main(String[] args) {
        launch(args);
        }
}