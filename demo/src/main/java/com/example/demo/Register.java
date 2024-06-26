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
import java.sql.*;
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
    @FXML
    private ComboBox<String> cityDropdown;

    //methods
    @FXML
    public void registerButtonOnAction(ActionEvent e)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demodb", "root","Asclepius1453");
            Statement statement = connection.createStatement();
            if (!checkPhoneNumberIsUsing(connection, statement) && !checkFieldsEmpty() && checkPhoneNumberIsTrue())
            {
                statement.executeUpdate("INSERT INTO user (Name, blood_Type, userPassword, phone_number,city) VALUES ('" + nameField.getText() + "','" + bloodTypeDropdown.getValue() + "', '" + passwordField.getText() + "', '" + phoneNumberField.getText() + "', '" + cityDropdown.getValue() +"')");
                showSuccessAlert();
            }
            else if (checkFieldsEmpty())
            {
                showErrorAlertForEmptyInputs();
                nameField.setText("");
                passwordField.setText("");
            }
            else if (!checkPhoneNumberIsTrue()){
                showErrorAlertWrongPhoneNumber();

            }
            else
            {
                showErrorAlertForAlreadyUsed();
            }

            phoneNumberField.setText("");



            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkPhoneNumberIsUsing (Connection connection, Statement statement)
    {
        try {
            ResultSet existingUser = statement.executeQuery("SELECT * FROM user WHERE phone_number = '" + phoneNumberField.getText() + "'");
            if (!existingUser.next()) {
                return false;
            }
            else
            {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkFieldsEmpty ()
    {
        return bloodTypeDropdown.getValue() == null || nameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || passwordField.getText().isEmpty();

    }

    @FXML
    public void Select (ActionEvent event)
    {
        String bloodType = bloodTypeDropdown.getSelectionModel().getSelectedItem().toString();
        String city = cityDropdown.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    public void loginDirectorButtonOnAction (ActionEvent event)
    {
        try {
            Stage stage = (Stage) loginPageDirectorButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            stage.setTitle("Login!");
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as appropriate
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> bloodTypeList = FXCollections.observableArrayList("ABRH+","ARH+","BRH+","0RH+","ABRH-","ARH-","BRH-","0RH-");
        ObservableList<String> cityList = FXCollections.observableArrayList("Istanbul", "Ankara", "Izmir");
        bloodTypeDropdown.setItems(bloodTypeList);
        cityDropdown.setItems(cityList);
    }

    public void showSuccessAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("Your Account has been created !");
        alert.setOnCloseRequest(event -> {
            // Open another page here
            try {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                Scene newScene = new Scene(fxmlLoader.load());
                stage.setTitle("Login!");
                stage.setScene(newScene);
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle the exception as appropriate
            }
        });

        alert.show();
    }

    public void showErrorAlertForAlreadyUsed()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Phone Number Already Exists");
        alert.setContentText("The phone number you entered is already registered.");
        alert.show();
    }

    public void showErrorAlertForEmptyInputs ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Do not leave any field blank !");
        alert.setContentText("Make Sure To Fill All Details To Continue...");
        alert.show();
    }

    public boolean checkPhoneNumberIsTrue(){

        String phoneNumber = phoneNumberField.getText();

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }

        if (phoneNumber.length() != 10) {
            return false;
        }

        return true;
    }

    public void showErrorAlertWrongPhoneNumber ()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please Check The Phone Number!");
        alert.setContentText("Enter your phone number without zero (5XXXXXXXXX)");
        alert.show();
    }

}
