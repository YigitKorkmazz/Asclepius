package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Register implements Initializable {
    //ui components
    @FXML
    private Button registerButton;

    @FXML
    private Button loginPageDirectorButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<String> bloodTypeDropdown;

    //methods
    @FXML
    public void registerButtonOnAction(ActionEvent e)
        {
            //TODO*
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11703300", "sql11703300", "QHBShewvQg");
                Statement statement = connection.createStatement();
                String phoneNumber = phoneNumberField.getText();
                ResultSet existingUser = statement.executeQuery("SELECT * FROM USER WHERE phone_number = '" + phoneNumber + "'");
                if (!existingUser.next()) {
                    statement.executeUpdate("INSERT INTO USER (username, blood_Type, password, phone_number) VALUES ('" + nameField.getText() + "', 'ARH+', '" + passwordField.getText() + "', '" + phoneNumber + "')");
                    showSuccessAlert();
                }
                else
                {
                    showErrorAlert();
                }

                // Close the connection
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    public void Select (ActionEvent event)
    {
        String s = bloodTypeDropdown.getSelectionModel().getSelectedItem().toString();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("ABRH+","ARH+","BRH+","0RH+","ABRH-","ARH-","BRH-","0RH-");
        bloodTypeDropdown.setItems(list);
    }

    public void showSuccessAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("Your Account has been created !");
        alert.setOnCloseRequest(event -> {
            // Open another page here
            try {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                Scene newScene = new Scene(fxmlLoader.load(), 600, 675);
                stage.setTitle("Login!");
                stage.setScene(newScene);
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle the exception as appropriate
            }
        });

        alert.show();
    }

    public void showErrorAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Phone Number Already Exists");
        alert.setContentText("The phone number you entered is already registered.");
        alert.show();
    }
}
