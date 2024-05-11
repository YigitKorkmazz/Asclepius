package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class Settings extends UserDAO{

    @FXML
    private TextField newPhoneNumberTf;

    @FXML
    private TextField newPasswordTf;

    @FXML
    private Label helloLabel;

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
    protected Label bloodTypeLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Button settingsButton;

    @FXML
    private Button homePageButton;

    @FXML
    private Button myDonationsButton;

    @FXML
    private Button myDonationRequestsButton;

    @FXML
    public void logOut()
    {
        Stage stage = (Stage) logOutBtn.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void goMyDonations ()
    {
        try {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyDonations.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("My Donations");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void goFeed()
    {
        try {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FeedPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("My Donations");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void goMyDonationRequests()
    {
        try {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyDonationRequests.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("My Donation Requests");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void initialize (){
        User currentUser = Feed.getCurrentUser();
        if (currentUser != null) {
            helloLabel.setText("Hello, " + currentUser.getName());
        } else {
            helloLabel.setText("Hello, Guest");
        }

        cityLabel.setText("Ankara");

        ObservableList<String> bloodTypeList = FXCollections.observableArrayList("ABRH+","ARH+","BRH+","0RH+","ABRH-","ARH-","BRH-","0RH-");
        ObservableList<String> cityList = FXCollections.observableArrayList("Istanbul","Ankara","Izmir");
        bloodTypeDropdown.setItems(bloodTypeList);
        cityDropdown.setItems(cityList);
    }

    @FXML
    public void firstChangeOnAction (ActionEvent event)
    {
        Stage stage = (Stage) oldPassword.getScene().getWindow(); // Get the main stage
        if (oldPasswordExistIsTrue(Feed.getCurrentUser(), oldPassword.getText()) &&
                checkPasswordsMatching(newPassword.getText(), newPasswordAgain.getText()) &&
                !arePasswordFieldsEmpty(newPassword.getText(), newPasswordAgain.getText(), oldPassword.getText()))
        {
            changePassword (newPassword.getText());
            updateUser(Feed.getCurrentUser());
        }
        else if (arePasswordFieldsEmpty(newPassword.getText(), newPasswordAgain.getText(), oldPassword.getText()))
        {
            showFieldsAreEmptyAlert (stage);
        }
        else if (!oldPasswordExistIsTrue(Feed.getCurrentUser(), oldPassword.getText()))
        {
            showOldPasswordIsWrongAlert (stage);
        }
        else if (!checkPasswordsMatching (newPassword.getText(), newPasswordAgain.getText()))
        {
            showPasswordsAreNotMatchingAlert (stage);
        }
    }

    @FXML
    public void secondChangeOnAction (ActionEvent event)
    {

    }

    public void changePassword (String newPassword)
    {
        Feed.getCurrentUser().setPassword(newPassword);
    }

    public void showOldPasswordIsWrongAlert (Stage ownerStage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(ownerStage); // Set the owner of the alert
        alert.setTitle("Error");
        alert.setHeaderText("Old password error");
        alert.setContentText("You wrote your old password wrongly!");
        alert.show();
    }

    public void showPasswordsAreNotMatchingAlert (Stage ownerStage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(ownerStage); // Set the owner of the alert
        alert.setTitle("Error");
        alert.setHeaderText("New password error");
        alert.setContentText("Your passwords are not matching!");
        alert.show();
    }

    public void showFieldsAreEmptyAlert (Stage ownerStage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(ownerStage); // Set the owner of the alert
        alert.setTitle("Error");
        alert.setHeaderText("Check Fields");
        alert.setContentText("Do not leave blanks empty!");
        alert.show();
    }

    public boolean checkPasswordsMatching (String password1, String password2)
    {
        return password1.equals(password2);
    }

    public boolean oldPasswordExistIsTrue (User user , String oldPassword)
    {
        return user.getPassword().equals(oldPassword);
    }

    public boolean  arePasswordFieldsEmpty (String password1, String password2, String oldPassword)
    {
        return password1.isEmpty() || password2.isEmpty() || oldPassword.isEmpty();
    }

    public void changeBloodType()
    {
        //User user = new User ()
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
