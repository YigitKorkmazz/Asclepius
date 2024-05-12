package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class DonationRequestCreation {

    @FXML
    public Label helloLabel;

    @FXML
    private Label bloodTypeLabel;

    @FXML
    public Label cityLabel;

    @FXML
    private Button bloodTypeChangeButton;

    @FXML
    private Button cityChangeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button myDonationsButton;

    @FXML
    private Button myDonationRequestsButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea addressArea;

    @FXML
    public void settingsOnAction()
    {
        try{
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void goSettingsPage1()
    {
        try{
            Stage stage = (Stage) bloodTypeChangeButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void goSettingsPage2()
    {
        try{
            Stage stage = (Stage) cityChangeButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            stage.setTitle("Settings");
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
    public void goMyDonations()
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
    public void initialize (){
        User currentUser = Feed.getCurrentUser();
        if (currentUser != null) {
            helloLabel.setText("Hello, " + currentUser.getName());
        } else {
            helloLabel.setText("Hello, Guest");
        }

        cityLabel.setText("Ankara");
    }
    @FXML
    public void publishButtonOnAction(ActionEvent event) {
        // Validate inputs
        String name = nameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String address = addressArea.getText().trim();

        if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Required Fields Missing");
            alert.setContentText("Please ensure all fields are filled out: Name, Phone Number, and Address.");
            alert.showAndWait();
            return;
        }

        User currentUser = Feed.getCurrentUser();
        DonationRequest newRequest = new DonationRequest(currentUser, phoneNumber, address, null, null, null, null, new ArrayList<User>());

        // Insert the donation request into the database
        BloodRequestDAO donationRequestDAO = new BloodRequestDAO();
        try {
            donationRequestDAO.insertDonationRequest(newRequest);
            System.out.println("Donation request published successfully.");
            // Optionally clear the fields or navigate away
        } catch (Exception e) {
            System.out.println("Failed to insert donation request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
