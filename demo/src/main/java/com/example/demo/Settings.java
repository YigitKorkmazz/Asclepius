package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Settings {
    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPasswordAgain;

    @FXML
    private Button firstChangeButton;

    @FXML
    private ComboBox <String> bloodTypeDropdown;

    @FXML
    private Label bloodType;

    @FXML
    private Label city;

    @FXML
    private ComboBox <String> cityDropdown;

    @FXML
    private Button secondChangeButton;

    @FXML
    private CheckBox ntfForRelevantRequests;

    @FXML
    private CheckBox ntfForTaggedRequests;

    @FXML
    private Button logOutBtn;

    @FXML
    public void logOut()
    {
        Stage stage = (Stage) logOutBtn.getScene().getWindow();
        stage.close();
    }

    public void changePassword()
    {
        //TODO
    }

    public void changeBloodType()
    {
        //TODO
    }

    public void changeCity ()
    {
        //TODO
    }

    public void changePhoneNumber()
    {
        //TODO
    }
}
