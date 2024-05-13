package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Feed{

    private static User currentUser;

    //ui components
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
    private VBox donationRequestsContainer;

    private BloodRequestDAO donationRequestDAO;

    // Rest of your class implementation...

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    //methods
    public void sortScarcity() {
        List<DonationRequest> donationRequests = donationRequestDAO.listAllBloodRequests();
        List<DonationRequest> sortedRequests = donationRequests.stream().sorted(Comparator.comparingInt(request -> request.getBloodType().ordinal())).collect(Collectors.toList());
        displayDonationRequests(sortedRequests);
    }


    // TO-DO: Widget Class Yap.

    public void listDonationRequest(DonationRequest request)
    {
        DonationRequestWidget widget = new DonationRequestWidget(request);
        donationRequestsContainer.getChildren().add(widget);
    }


    // TO-DO: Widget Class Yap.
    public void sortDonationRequests(List<DonationRequest> requests) {
        List<DonationRequest> sortedRequests = requests.stream().sorted(Comparator.comparingInt(request -> request.getBloodType().ordinal())).collect(Collectors.toList());
        displayDonationRequests(sortedRequests);
    }

    // TO-DO: Widget Class Yap.
    public void displayDonationList() {
        List<DonationRequest> donationRequests = donationRequestDAO.listAllBloodRequests();
        displayDonationRequests(donationRequests);
    }

    private void displayDonationRequests(List<DonationRequest> requests) {
        donationRequestsContainer.getChildren().clear();
        for (DonationRequest request : requests) {
            listDonationRequest(request);
        }
    }
/*
    public void openDonationRequest(DonationRequest request)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("donationRequestScreen.fxml"));
            Scene scene = new Scene(loader.load());
            DonationRequestScreen controller = loader.getController();
            controller.setDonationRequest(request);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Donation Request");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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

    public boolean isMatching ()
    {
        return true;
    }

    @FXML
    public void initialize (){
        /*List <DonationRequest> requests = donationRequestDAO.listAllBloodRequests();
        for (DonationRequest item: requests)
        {
            if (isMatching())
            {

            }
        }*/
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            helloLabel.setText("Hello, " + currentUser.getName());
        } else {
            helloLabel.setText("Hello, Guest");
        }

        cityLabel.setText(currentUser.getCityAsString());
        bloodTypeLabel.setText(currentUser.getBloodTypeAsString());
    }
}