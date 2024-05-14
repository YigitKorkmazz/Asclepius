package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Button returnMyDonationRequest;

    @FXML
    private Button myDonationRequestsButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea addressArea;

    @FXML
    private Button publishButton;

    @FXML
    private ComboBox<String> cityDropdown;

    @FXML
    private ComboBox<String> bloodTypeDropdown;

    @FXML
    private CheckBox transportationHelpCb;

    @FXML
    private ToggleGroup Amount;

    @FXML
    private RadioButton zeroUsd;

    @FXML
    private RadioButton fiftyUsd;

    @FXML
    private RadioButton hundredUsd;

    private DonationRequest newRequest;

    private BloodRequestDAO bloodRequestDAO;

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
    public void initialize (){
        User currentUser = Feed.getCurrentUser();
        helloLabel.setText("Hello, " + currentUser.getName());
        cityLabel.setText(currentUser.getCityAsString());
        bloodTypeLabel.setText (currentUser.getBloodTypeAsString());
        ObservableList<String> bloodTypeList = FXCollections.observableArrayList("ABRH+","ARH+","BRH+","0RH+","ABRH-","ARH-","BRH-","0RH-");
        ObservableList<String> cityList = FXCollections.observableArrayList("Istanbul","Ankara","Izmir");
        bloodTypeDropdown.setItems(bloodTypeList);
        cityDropdown.setItems(cityList);
    }
    @FXML
    public void publishButtonOnAction(ActionEvent event) throws SQLException {
        // Validate inputs
        String name = nameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String address = addressArea.getText().trim();
        String bloodType = bloodTypeDropdown.getValue();
        String city = cityDropdown.getValue();
        DonationRequest.TransportationAssist transAssist = DonationRequest.TransportationAssist.No;
        DonationRequest.MoneyAssist moneyAssist= DonationRequest.MoneyAssist.ZERO;

        if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText("Required Fields Missing");
            alert.setContentText("Please ensure all fields are filled out: Name, Phone Number, and Address.");
            alert.showAndWait();
            return;
        }

        if (transportationHelpCb.isSelected())
        {
            transAssist = DonationRequest.TransportationAssist.Yes;
        }

        if (fiftyUsd.isSelected())
        {
            moneyAssist = DonationRequest.MoneyAssist.FIFTY;
        }
        else if (hundredUsd.isSelected())
        {
            moneyAssist = DonationRequest.MoneyAssist.HUNDRED;
        }
        bloodRequestDAO = new BloodRequestDAO();
        Statement statement = bloodRequestDAO.getConnection().createStatement();
        User currentUser = Feed.getCurrentUser();
        newRequest = new DonationRequest(currentUser, phoneNumber, address, DonationRequest.City.valueOf(city), DonationRequest.convertStringTypeToEnum(bloodType), transAssist, moneyAssist, new ArrayList<User>(), name);
        ResultSet request_id = statement.executeQuery("SELECT donation_id FROM Donations WHERE phone_number = '" + phoneNumberField.getText() + "' AND address = '" + addressArea.getText() + "' AND patient_name = '" + name + "'");
        if (request_id.next()) {
            int id = request_id.getInt("donation_id");
            newRequest.setUniqueId (id);
        }
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
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        showDonationCreatedAlert(stage);

    }

    public void showDonationCreatedAlert (Stage ownerStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(ownerStage);
        alert.setTitle("Information Dialog");
        alert.setContentText("Donation created");
        alert.show();
    }
}
