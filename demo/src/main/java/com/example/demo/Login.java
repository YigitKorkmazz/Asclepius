package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login {

    @FXML
    private Button loginButton;

    @FXML
    private Button registrationDirectorButton;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonOnAction(ActionEvent e) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://myFirstProject_willingmen:c45cce85f4f1feff87e1055d85bd97153672d7bb@tzq.h.filess.io:3307/myFirstProject_willingmen", "myFirstProject_willingmen", "c45cce85f4f1feff87e1055d85bd97153672d7bb");
            Statement statement = connection.createStatement();
            if (isEmptyLoginButtons()) {
                showAlertForEmptyCredidentials();
            } else {
                ResultSet existingUser = statement.executeQuery("SELECT * FROM user WHERE phone_number = '" + phoneNumberField.getText() + "' AND userPassword = '" + passwordField.getText() + "'");
                if (existingUser.next()) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                    Stage primaryStage = new Stage();
                    User user = new User(existingUser.getString("blood_type"), existingUser.getString("Name"), phoneNumberField.getText(), passwordField.getText(), "Ankara");

                    // User ID is set.
                    ResultSet userId = statement.executeQuery("SELECT User_id FROM user WHERE phone_number = '" + phoneNumberField.getText() + "'");
                    if (userId.next()) {
                        System.out.println ("USER ID BURAYA GIRDI");
                        int id = userId.getInt("User_id");
                        user.setUniqueId(id);
                    }
                    user.setCity("Ankara");

                    Feed.setCurrentUser(user);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FeedPage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
                    stage.setTitle("Feed");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    showAlertForWrongCredidentials();
                    phoneNumberField.setText("");
                    passwordField.setText("");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void directRegistration ()
    {
        try{
            Stage stage = (Stage) registrationDirectorButton.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 675);
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showAlertForEmptyCredidentials ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please Fill All The Empty Blanks");
        alert.setContentText("Fill Credidentials!");
        alert.show();
    }
    public void showAlertForWrongCredidentials ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please Check The Credidentials!");
        alert.setContentText("Enter your phone number without zero (5XXXXXXXXX)");
        alert.show();
    }

    public boolean isEmptyLoginButtons(){

        if ( passwordField.getText().isEmpty() || phoneNumberField.getText().isEmpty()){
            return true;
        }

        return false;
    }


}
