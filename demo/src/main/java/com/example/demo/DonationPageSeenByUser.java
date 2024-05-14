package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DonationPageSeenByUser {

    //ui components
    @FXML
    private Label bloodTypeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label transportationHelp;

    @FXML
    private Label moneyHelpField;

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

    @FXML
    public void tagFriend()
    {
        //TODO
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
    public void closeDonationMethod()
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
}
