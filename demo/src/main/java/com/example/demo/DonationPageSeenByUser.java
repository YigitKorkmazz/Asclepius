package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DonationPageSeenByUser implements Initializable {

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
    private  DonationRequest currentRequest;
    private String notifyUser = "";

    //methods

    @FXML
    public void acceptDonation()
    {
        donationRequestDAO = new BloodRequestDAO();
        donationRequestDAO.updateAcceptedUser(Feed.getCurrentUser().getUniqueId(), currentRequest);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("You have accepted this donation request.");
        alert.showAndWait();
        try {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FeedPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("My Donations");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addNotification(currentRequest.getCreatorUser().getPhoneNumber(), "ACCEPT");
    }

    @FXML
    public void retreatDonation() {
        if (currentRequest != null) {
            boolean foundUser = false;
            for (User user : currentRequest.getUsersAcceptedList()) {
                if (user.getUniqueId() == Feed.getCurrentUser().getUniqueId()) {
                    foundUser = true;
                    donationRequestDAO = new BloodRequestDAO();
                    donationRequestDAO.updateRetreatedDonationForUser(Feed.getCurrentUser().getUniqueId(), currentRequest);
                    currentRequest.getUsersAcceptedList().remove(user);
                    donationRequestDAO.deleteUserFromAcceptedList(currentRequest.getUniqueId(), Feed.getCurrentUser().getUniqueId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("You have retreated from the donation request.");
                    alert.showAndWait();
                    break;
                }
            }
            if (!foundUser) {
                // User has not accepted the donation request, retreat is not allowed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You have not accepted this donation request.");
                alert.showAndWait();
            }
        } else {
            // No donation request selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No donation request selected.");
            alert.showAndWait();
        }

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


    public void tagFriend() {
        Dialog<String> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Tag a Friend");

        // Set up the input field
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter your friend's phone number");

        // Set up buttons
        ButtonType sendButtonType = new ButtonType("Send");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = Feed.getCurrentUser();
        if (currentUser != null && helloLabel != null) {
            helloLabel.setText("Hello, " + currentUser.getName());
        } else if (helloLabel != null) {
            helloLabel.setText("Hello, Guest");
        }
        if (cityChangeButton != null)
        {
            cityChangeButton.setText(currentUser.getCityAsString());
        }
        if (bloodTypeChangeButton != null)
        {
            bloodTypeChangeButton.setText(currentUser.getBloodTypeAsString());
        }

    }

    public void setData (DonationRequest request, boolean isPhoneNumberSeen)
    {
        this.currentRequest = request;
        if ( request != null)
        {
            bloodTypeOfPatient.setText (request.getBloodTypeAsString());
            nameLabel.setText (request.getNameOfPatient());

            if ( isPhoneNumberSeen){
               setNumberVisible(request);

            }else{
                phoneNumberLabel.setText ("(+90) **********" );
            }
            locationLabel.setText (request.getAddress());
            if (request.getTransportationAssist().equals(DonationRequest.TransportationAssist.No))
            {
                transportationHelp.setVisible(false);
            }
            if (request.getMoneyAssistAsString().equals("0 usd"))
            {
                moneyHelpField.setVisible(false);
            }
            else
            {
                moneyHelpField.setText (request.getMoneyAssistAsString());
            }
        }

    }

    public void setNumberVisible(DonationRequest request){
        this.phoneNumberLabel.setText("(+90) " + request.getPhoneNumberAssc());
    }

    public void setRetreatDisabled ()
    {
        this.retreatButton.setDisable(true);
    }

    public void setAcceptDisabled ()
    {
        this.acceptButton.setDisable (true);
        this.acceptButton.setText ("Accepted");
    }

    public void setAcceptEnabled ()
    {
        this.acceptButton.setDisable(false);
    }

}

