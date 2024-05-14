package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DonationRequestScreen {

    //ui components
    @FXML
    private Label helloLabel;

    @FXML
    private Label bloodTypeLabel;

    @FXML
    private Label cityLabel;

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
    public static DonationRequest currentDonation;

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
    public void editDonation(DonationRequest donation)
    {
        try {
            Stage stage = (Stage) editButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DonationRequestCreation.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            stage.setTitle("Donation Request Edit");
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

    @FXML
    public void initialize() {
        User currentUser = Feed.getCurrentUser();
        bloodType.setText (currentDonation.getBloodTypeAsString());
        patientNameLabel.setText (currentDonation.getNameOfPatient());
        patientPhoneNumberLabel.setText ("(+90) " + currentDonation.getPhoneNumberAssc());
        addressLabel.setText (currentDonation.getAddress());
        if (currentDonation.getTransportationAssist().equals(DonationRequest.TransportationAssist.No))
        {
            transportationHelp.setVisible(false);
        }
        if (currentDonation.getMoneyAssistAsString().equals("0 usd"))
        {
            moneyHelpLabel.setVisible(false);
        }
        else
        {
            moneyHelpLabel.setText (currentDonation.getMoneyAssistAsString());
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
