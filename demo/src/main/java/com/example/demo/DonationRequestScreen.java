package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DonationRequestScreen implements Initializable {

    //ui components
    @FXML
    private Label helloLabel;

    @FXML
    private Label bloodTypeLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Button goBackButton;

    @FXML
    private Button transportationHelp;

    @FXML
    private Button moneyHelpLabel;

    @FXML
    private Button closeButton;

    @FXML
    private Button editButton;

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
    private Label bloodType;

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label patientPhoneNumberLabel;

    @FXML
    private Label addressLabel;

    private BloodRequestDAO donationRequestDAO;
    private DonationRequest currentDonation;


    //methods
    @FXML
    public void displayDonationRequest()
    {
        //TODO
    }

    @FXML
    public void closeDonationMethod ()
    {
        donationRequestDAO = new BloodRequestDAO();
        donationRequestDAO.deleteDonationRequest(currentDonation.getUniqueId());

        try {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyDonationRequests.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("My Donation Requests");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void editDonation()
    {

        try {
            Stage stage = (Stage) editButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("DonationCreationPage.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("Donation Request Edit");

            DonationRequestCreation creationPage = fxmlLoader.getController();
            creationPage.setData(currentDonation);
            if (creationPage == null) {
                System.err.println("Error: DonationRequestCreation controller is null");
                return;
            }

            donationRequestDAO = new BloodRequestDAO();
            donationRequestDAO.deleteDonationRequest(currentDonation.getUniqueId());
            DonationRequestCreation donationRequestCreation = fxmlLoader.getController();
            donationRequestCreation.setData(currentDonation);
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
    public void goMyDonationRequests1()
    {
        try {
            Stage stage = (Stage) goBackButton.getScene().getWindow();
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
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
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

    public void setData (DonationRequest request) {
        this.currentDonation = request;
        bloodType.setText(request.getBloodTypeAsString());
        patientNameLabel.setText(request.getNameOfPatient());
        patientPhoneNumberLabel.setText("(+90) " + request.getPhoneNumberAssc());
        addressLabel.setText(request.getAddress());

        if (request.getTransportationAssist().equals(DonationRequest.TransportationAssist.No)) {
            transportationHelp.setVisible(false);
        }
        if (request.getMoneyAssistAsString().equals("0 usd")) {
            moneyHelpLabel.setVisible(false);
        } else {
            moneyHelpLabel.setText(request.getMoneyAssistAsString());
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
}