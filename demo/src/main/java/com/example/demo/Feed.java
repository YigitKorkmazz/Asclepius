package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;


public class Feed{

    private static User currentUser;

    @FXML
    public VBox VBoxforRequests;
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

    private static BloodRequestDAO donationRequestDAO;

    // Rest of your class implementation...

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    //methods
    public void sortScarcity(List<DonationRequest> requests) {
        requests.sort(Comparator.comparing(DonationRequest::getBloodType));
    }

    public void sortMatching(List<DonationRequest> requests, User currentUser) {
        String userBloodType = currentUser.getBloodTypeAsString();

        requests.sort((r1, r2) -> {
            boolean isMatch1 = r1.getBloodTypeAsString().equals(userBloodType);
            boolean isMatch2 = r2.getBloodTypeAsString().equals(userBloodType);

            if (isMatch1 && !isMatch2) {
                return -1;
            } else if (!isMatch1 && isMatch2) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    public boolean isMatching (DonationRequest request, User user)
    {   String userCity = user.getCityAsString().toLowerCase();
        String donationCity = request.getCityAsString().toLowerCase();
        return userCity.equals(donationCity);
    }
    public void sortCity(List<DonationRequest> requests, User currentUser) {
        requests.sort((r1, r2) -> {
            boolean isMatch1 = isMatching(r1, currentUser);
            boolean isMatch2 = isMatching(r2, currentUser);

            if (isMatch1 && !isMatch2) {
                return -1;
            } else if (!isMatch1 && isMatch2) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    @FXML
    public void settingsOnAction (ActionEvent event)
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

    @FXML
    public void goSettingsPage1 (ActionEvent event)
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

    @FXML
    public void goSettingsPage2 (ActionEvent event)
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

    List <DonationRequest> currentRequests;

    private static boolean noMoreNotification = false;

    public static void sendNoMoreNotification(){
        noMoreNotification = true;
    }

    public static void checkMatch() {
        if (!noMoreNotification) {
            List<DonationRequest> currentRequests = donationRequestDAO.listAllBloodRequests();
            boolean matching = false;
            for (DonationRequest dr : currentRequests) {
                if (dr.getCityAsString().equals(currentUser.getCityAsString())
                        || dr.getBloodTypeAsString().equals(currentUser.getBloodTypeAsString())) {
                    matching = true;
                }
            }
            if (matching) {
                UserDAO userDAO = new UserDAO();
                int userId = currentUser.getUniqueId();
                if (userId != -1) {
                    String notifyUser = "" + -1 + " " + Feed.getCurrentUser().getUniqueId() + " " + "MATCH";
                    userDAO.updateNotification(userId, notifyUser);
                }
            }
        }
    }




    @FXML
    public void initialize () {

        NotificationHandler notificationHandler = new NotificationHandler(new UserDAO(), currentUser.getUniqueId(), settingsButton);
        notificationHandler.startScheduledNotification();

        donationRequestDAO = new BloodRequestDAO();
        List<DonationRequest> requests = donationRequestDAO.listAllBloodRequests();
        currentRequests = requests;
        User currentUser = getCurrentUser();

        sortScarcity(requests);
        sortMatching(requests, currentUser);
        sortCity(requests, currentUser);

        for (DonationRequest item : requests) {
            item.setUsersAcceptedList(donationRequestDAO.acceptedUsers(item));
            System.out.println("ITEM NAME " + item.getNameOfPatient() + " ID " + item.getUniqueId());

            if (donationRequestDAO.getUserByDonationID(item.getUniqueId()).getUniqueId() != currentUser.getUniqueId() && VBoxforRequests != null) {
                System.out.println("ITEM NAME " + item.getNameOfPatient() + "icerdeyim!");
                if (item.getUsersAcceptedList().isEmpty()) {
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
                else {
                    boolean isOneOfMyAccepted = false;
                    for (int i = 0; i < item.getUsersAcceptedList().size(); i++) {
                        if (item.getUsersAcceptedList().get(i).getUniqueId() == currentUser.getUniqueId()) {
                            isOneOfMyAccepted = true;
                            break;
                        }
                    }
                     if (!isOneOfMyAccepted)
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



            if (helloLabel != null) {
                helloLabel.setText("Hello, " + currentUser.getName());
            }
            if (cityChangeButton != null) {
                cityChangeButton.setText(currentUser.getCityAsString());
            }
            if (bloodTypeChangeButton != null) {
                bloodTypeChangeButton.setText(currentUser.getBloodTypeAsString());
            }
        }

    }