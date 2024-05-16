package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationHandler {

    private final UserDAO userDAO;
    private final int taggedUserId;
    private final Button triggerButton;
    private static BloodRequestDAO donationRequestDAO;

    private boolean isComingFromMyDonations = false;


    public NotificationHandler(UserDAO userDAO, int taggedUserId, Button triggerButton) {
        this.userDAO = userDAO;
        this.taggedUserId = taggedUserId;
        this.triggerButton = triggerButton;
    }



    public void startScheduledNotification() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule to run every second
        scheduler.scheduleAtFixedRate(() -> {

            
            try {
                System.out.println("Scheduler task running at: " + System.currentTimeMillis());
                String notification = userDAO.getNotification(taggedUserId);
                boolean haveNotification = !notification.isEmpty();

                if (haveNotification) {
                    System.out.println("Notification found: " + notification);
                    Platform.runLater(() -> sendNotification(notification));
                    userDAO.updateNotification(taggedUserId, "");
                } else {
                    System.out.println("No new notifications.");
                }
            } catch (SQLException e) {
                System.err.println("SQL Exception in scheduled task: " + e.getMessage());
                // Consider reconnecting or other recovery mechanisms here
            } catch (Exception e) {
                System.err.println("General Exception in scheduled task: " + e.getMessage());
            }


        }, 0, 5, TimeUnit.SECONDS);
    }

    private void sendNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.initStyle(StageStyle.UTILITY);
        notificationStage.setTitle("Notification");
        notificationStage.initStyle(StageStyle.UNDECORATED);

        String[] input = message.split(" ");
        int donationID = Integer.parseInt(input[0]);
        int userID = Integer.parseInt(input[1]);
        String type = input[2];

        Label messageLabel = new Label();
        switch (type) {
            case "ACCEPT" -> messageLabel.setText(" Your donation was accepted.");
            case "TAG" -> messageLabel.setText("You were tagged to a donation.");
            case "MATCH" -> messageLabel.setText("There are some donations you match with!");
        }
        messageLabel.setStyle("-fx-text-fill: white;");

        Button goToNotificationButton = new Button("Go to the post!");
        goToNotificationButton.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-radius: 30px; " +
                        "-fx-background-radius: 30px;");

        Button dismissButton = new Button("Dismiss");
        dismissButton.setStyle(
                "-fx-background-color: #f53a40; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 30px; " +
                        "-fx-background-radius: 30px;");

        goToNotificationButton.setOnAction(e -> {
            System.out.println("Go to the post!");
            if (type.equals("MATCH")) {
                Feed.sendNoMoreNotification();
                try {
                    Stage stage = getPrimaryStage();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FeedPage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
                    if (stage != null) {
                        stage.setTitle("My Donations");
                        stage.setScene(scene);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    donationRequestDAO = new BloodRequestDAO();
                    DonationRequest donationRequest = donationRequestDAO.getRequestById(donationID);
                    for (int i = 0 ; i < donationRequest.getUsersAcceptedList().size(); i++)
                    {
                        if (donationRequestDAO.getRequestById(donationID).getUniqueId() == Feed.getCurrentUser().getUniqueId())
                        {
                            isComingFromMyDonations = true;
                            break;
                        }
                    }
                    System.out.println();
                    System.out.println(donationRequestDAO.getUserByDonationID(donationID).getUniqueId());
                    System.out.println();

                    FXMLLoader fxmlLoader = null;
                    Scene newScene = null;
                    if (Feed.getCurrentUser().getUniqueId() == donationRequestDAO.getUserByDonationID(donationID).getUniqueId()) {
                        fxmlLoader = new FXMLLoader(getClass().getResource("CreatorsRequestPage.fxml"));
                        newScene = new Scene(fxmlLoader.load(), 1200, 800);
                        DonationRequestScreen donationRequestScreen = fxmlLoader.getController();
                        donationRequestScreen.setData(donationRequest);
                    } else {
                        fxmlLoader = new FXMLLoader(getClass().getResource("DonationPageSeenByUser.fxml"));
                        newScene = new Scene(fxmlLoader.load(), 1200, 800);
                        DonationPageSeenByUser donationPageSeenByUser = fxmlLoader.getController();
                        donationPageSeenByUser.setData(donationRequest, false);
                        System.out.println("SET DATA WORKED");
                        if (isComingFromMyDonations)
                        {
                            donationPageSeenByUser.setAcceptDisabled();
                        }
                        else
                        {
                            donationPageSeenByUser.setAcceptEnabled();
                            donationPageSeenByUser.setRetreatDisabled();
                        }

                    }
                    Stage primaryStage = getPrimaryStage();
                    if (primaryStage != null) {
                        primaryStage.setTitle("Donation Request");
                        primaryStage.setScene(newScene);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            notificationStage.close();
        });

        dismissButton.setOnAction(e -> {
            notificationStage.close();
            if (type.equals("MATCH")) {
                Feed.sendNoMoreNotification();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-color: black;");
        layout.getChildren().addAll(messageLabel, goToNotificationButton, dismissButton);

        Scene scene = new Scene(layout, 300, 150);
        notificationStage.setScene(scene);
        notificationStage.showAndWait();
    }

    private Stage getPrimaryStage() {
        return (Stage) triggerButton.getScene().getWindow();
    }
}