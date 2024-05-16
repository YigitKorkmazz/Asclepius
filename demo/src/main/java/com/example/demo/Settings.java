package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings extends UserDAO{

    @FXML
    private TextField newPhoneNumberTf;

    @FXML
    private TextField validationPasswordTf;

    @FXML
    private Label helloLabel1;

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
    private Button logOutBtn;

    @FXML
    private Button bloodChangeButton;

    @FXML
    private Button cityChangeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button homePageButton;

    @FXML
    private Button myDonationsButton;

    @FXML
    private Button myDonationRequestsButton;

    private BloodRequestDAO dao;
    @FXML
    private Button deleteTheAccountButton;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void logOut()
    {
        Stage stage = (Stage) logOutBtn.getScene().getWindow();
        stage.close();
        System.exit(0);
    }


    @FXML
    public void goMyDonations ()
    {
        try {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyDonations.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("My Donations");
            stage.setScene(scene);
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
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("My Donations");
            stage.setScene(scene);
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
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("My Donation Requests");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void initialize (){
        User currentUser = Feed.getCurrentUser();
        if (currentUser != null && helloLabel1 != null) {
            helloLabel1.setText("Hello, " + currentUser.getName());
        } else if (helloLabel1 != null) {
            helloLabel1.setText("Hello, Guest");
        }
        if (cityChangeButton != null)
        {
            cityChangeButton.setText(currentUser.getCityAsString());
        }
        if (bloodChangeButton != null)
        {
            bloodChangeButton.setText(currentUser.getBloodTypeAsString());
        }
        bloodChangeButton.setText(currentUser.getBloodTypeAsString());
        city.setText(currentUser.getCityAsString());
        ObservableList<String> bloodTypeList = FXCollections.observableArrayList("ABRH+","ARH+","BRH+","0RH+","ABRH-","ARH-","BRH-","0RH-");
        ObservableList<String> cityList = FXCollections.observableArrayList("Istanbul","Ankara","Izmir");
        bloodTypeDropdown.setItems(bloodTypeList);
        cityDropdown.setItems(cityList);

        ntfForRelevantRequests.setSelected(!userDAO.getNotificationStatus(Feed.getCurrentUser().getUniqueId()));
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
        Stage stage = (Stage) oldPassword.getScene().getWindow(); // Get the main stage
        if (!validationPasswordTf.getText().isEmpty() && checkPasswordIsCorrect(validationPasswordTf.getText())) {
            if (bloodTypeDropdown.getValue() != null) {
                changeBloodType();
            }
            if (cityDropdown.getValue() != null) {
                changeCity();
            }
            if (!newPhoneNumberTf.getText().isEmpty())
            {
                changePhoneNumber();
            }
            showSuccessfullyChangedAlert(stage);
            makeFieldsEmpty();
        }
        else if (checkPasswordIsCorrect(validationPasswordTf.getText()))
        {
            showPasswordIsWrongAlert(stage);
        }
        else{
            showFieldsAreEmptyAlert(stage);
        }
    }

    @FXML
    public void deleteTheAccount (ActionEvent Event)
    {


        Stage stage = (Stage) deleteTheAccountButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage); // Set the owner of the alert
        alert.setTitle("Account Deletion");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure about deleting your account ? ");
        alert.setOnCloseRequest(event -> {
            User user = Feed.getCurrentUser();
            dao = new BloodRequestDAO();
            List<DonationRequest> requests = dao.listAllBloodRequests();

            for ( DonationRequest item : requests)
            {
                if (dao.getUserByDonationID(item.getUniqueId()).getUniqueId() == user.getUniqueId())
                {
                    dao.deleteDonationRequest(item.getUniqueId());
                }
            }
            userDAO.deleteUser(Feed.getCurrentUser().getUniqueId());
            stage.close();
            System.exit(0);
        });
        alert.show();
    }

    public void changePassword(String newPassword) {
        User currentUser = Feed.getCurrentUser();
        currentUser.setPassword(newPassword); // Update password in memory
        userDAO.updateUser(currentUser);
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

    public void showSuccessfullyChangedAlert(Stage ownerStage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(ownerStage);
        alert.setTitle("Information Dialog");
        alert.setContentText("Your changes Are saved");
        alert.show();
    }

    public void makeFieldsEmpty ()
    {
        newPhoneNumberTf.setText("");
        validationPasswordTf.setText("");
        oldPassword.setText("");
        newPassword.setText("");
        newPasswordAgain.setText("");
    }

    public void showPasswordIsWrongAlert (Stage ownerStage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(ownerStage); // Set the owner of the alert
        alert.setTitle("Error");
        alert.setHeaderText("Check Password");
        alert.setContentText("Your password is wrong!");
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

    public boolean arePasswordFieldsEmpty (String password1, String password2, String oldPassword)
    {
        return password1.isEmpty() || password2.isEmpty() || oldPassword.isEmpty();
    }

    public boolean checkPasswordIsCorrect (String password)
    {
        return password.equals(Feed.getCurrentUser().getPassword());
    }

    public void changeBloodType()
    {
        User currentUser = Feed.getCurrentUser();
        currentUser.setBloodType(bloodTypeDropdown.getValue()); // Update password in memory
        userDAO.updateUser(currentUser);
    }

    public void changeCity ()
    {
        User currentUser = Feed.getCurrentUser();
        currentUser.setCity(cityDropdown.getValue()); // Update password in memory
        userDAO.updateUser(currentUser);
    }

    public void changePhoneNumber()
    {
        User currentUser = Feed.getCurrentUser();
        currentUser.setPhoneNumber(newPhoneNumberTf.getText()); // Update password in memory
        userDAO.updateUser(currentUser);
    }

    @FXML
    public void notifPressed(){

        System.out.println(ntfForRelevantRequests.isSelected());
        userDAO.updateNotificationStatus(Feed.getCurrentUser().getUniqueId(), ntfForRelevantRequests.isSelected());
    }
}
