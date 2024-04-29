package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Register implements Initializable {
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
    public void registerButtonOnAction(ActionEvent e)
    {
        //TODO
        //The information of the user will be stored in database and account will be created.
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
}
