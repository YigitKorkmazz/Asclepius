package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DonationPageSeenByUser {

    //ui components

    @FXML
    private Label helloLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label bloodTypeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Button transportationHelp;

    @FXML
    private Button moneyHelpField;

    @FXML
    private Button acceptButton;

    @FXML
    private Button retreatButton;

    @FXML
    private Button tagAFriendButton;

    @FXML
    private Button cityChangeButton;

    @FXML
    private Button bloodTypeChangeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button myDonationsButton;

    @FXML
    private Button myDonationRequestsButton;

    @FXML
    private Label bloodTypeOfPatient;

    private BloodRequestDAO donationRequestDAO;
    public static DonationRequest currentRequest;
    private String notifyUser = "";

    //methods
    @FXML
    public void displayDonationRequest()
    {
        //TODO
    }

    @FXML
    public void acceptDonation()
    {
        //TODO
    }

    @FXML
    public void retreatDonation()
    {
        //TODO
    }

    public void tagFriend() {
        Dialog<String> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Tag a Friend");

        // Set up the input field
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter your friend's phone number");

        // Set up buttons
        ButtonType sendButtonType = new ButtonType("Send");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        dialog.getDialogPane().getButtonTypes().addAll(sendButtonType, cancelButtonType);

        // Layout
        VBox vbox = new VBox();
        vbox.getChildren().add(phoneNumberField);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a phone number when the send button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButtonType) {
                return phoneNumberField.getText();
            }
            return null;
        });

        // Show dialog and get result
        String phoneNumber = dialog.showAndWait().orElse(null);

        // Handle the result
        if (phoneNumber != null) {
            addNotification(phoneNumber, "TAG");
        }
    }

    public void addNotification(String phoneNumber, String type) {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.findUserIdByPhoneNumber(phoneNumber);
        if (userId != -1) {
            String notifyUser = "" + currentRequest.getUniqueId() + " " + Feed.getCurrentUser().getUniqueId() + " " + type;
            userDAO.updateNotification(userId, notifyUser);

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setContentText("Notification sent to your friend.");
            infoAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User does not exist.");
            alert.showAndWait();
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
    public void closeDonationMethod()
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

    public void goMyDonations()
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
    public void settingsOnAction()
    {
        try{
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
            stage.setTitle("Settings");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void goSettingsPage1()
    {
        try{
            Stage stage = (Stage) bloodTypeChangeButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("Settings");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void goSettingsPage2()
    {
        try{
            Stage stage = (Stage) cityChangeButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("Settings");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        User currentUser = Feed.getCurrentUser();
        if ( currentRequest != null)
        {
            bloodTypeOfPatient.setText (currentRequest.getBloodTypeAsString());
            nameLabel.setText (currentRequest.getNameOfPatient());
            phoneNumberLabel.setText ("(+90) " + currentRequest.getPhoneNumberAssc());
            locationLabel.setText (currentRequest.getAddress());
            if (currentRequest.getTransportationAssist().equals(DonationRequest.TransportationAssist.No))
            {
                transportationHelp.setVisible(false);
            }
            if (currentRequest.getMoneyAssistAsString().equals("0 usd"))
            {
                moneyHelpField.setVisible(false);
            }
            else
            {
                moneyHelpField.setText (currentRequest.getMoneyAssistAsString());
            }
        }


        if (currentUser != null && helloLabel != null) {
            helloLabel.setText("Hello, " + currentUser.getName());
        } else if (helloLabel != null) {
            helloLabel.setText("Hello, Guest");
        }
        if (cityLabel != null)
        {
            cityLabel.setText(currentUser.getCityAsString());
        }
        if (bloodTypeLabel != null)
        {
            bloodTypeLabel.setText(currentUser.getBloodTypeAsString());
        }


    }
}

