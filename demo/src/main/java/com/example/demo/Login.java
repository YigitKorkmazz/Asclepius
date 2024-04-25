package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Login {

    @FXML
    private Button loginButton;

    @FXML
    public void loginButtonOnAction (ActionEvent e)
    {
        try{
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        /*Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();*/

    }

}
