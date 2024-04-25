package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {
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
    public void registerButtonOnAction(ActionEvent e)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/designdb", "root", "Asclepius1453");
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO user (username, blood_Type, password, phone_number) VALUES ('" + nameField.getText() + "', 'arh+', '" + passwordField.getText() + "', '" + phoneNumberField.getText() + "')");

            // statement.executeUpdate("UPDATE user SET blood_Type = '0rh+'");
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                System.out.println("name : " + resultSet.getString("username") + " " +
                        "blood type: " + resultSet.getString("blood_Type") +
                        "phone number: " + resultSet.getString("phone_number"));
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


}
