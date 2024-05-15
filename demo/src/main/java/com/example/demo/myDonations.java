package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class myDonations {

    @FXML
    private Label helloLabel;

    @FXML
    protected Label bloodTypeLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Button bloodTypeChangeButton;

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

    @FXML
    private VBox VBoxforRequests;

    private BloodRequestDAO donationRequestDAO;

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
        donationRequestDAO = new BloodRequestDAO();
        List<DonationRequest> requests = donationRequestDAO.listAllBloodRequests();
        User currentUser = Feed.getCurrentUser();
        for (DonationRequest item : requests) {
            item.setUsersAcceptedList(donationRequestDAO.acceptedUsers(item));
             for (int i = 0 ; i < item.getUsersAcceptedList().size(); i++)
             {
                 if (item.getUsersAcceptedList().get(i).getUniqueId() == currentUser.getUniqueId())
                 {
                     try {
                         FXMLLoader loader = new FXMLLoader();
                         loader.setLocation(getClass().getResource("Item.fxml"));
                         HBox itemBox = loader.load();
                         ItemController itemController = loader.getController();
                         itemController.setData(item);
                         VBoxforRequests.getChildren().add(itemBox);

                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
        }
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
