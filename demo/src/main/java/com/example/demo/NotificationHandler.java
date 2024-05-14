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
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void sendNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.initStyle(StageStyle.UTILITY);
        notificationStage.setTitle("Notification");

        String[] input = message.split(" ");
        int donationID = Integer.parseInt(input[0]);
        int userID = Integer.parseInt(input[1]);
        String type = input[2];

        Label messageLabel = new Label();
        switch (type) {
            case "ACCEPT" -> messageLabel.setText("Your donation was accepted");
            case "TAG" -> messageLabel.setText("You were tagged");
            case "MATCH" -> messageLabel.setText("There are some donations you match with");
        }

        Button goToNotificationButton = new Button("Go to the notification");
        Button dismissButton = new Button("Dismiss");

        goToNotificationButton.setOnAction(e -> {
            System.out.println("Go to the notification clicked");
            try {
                donationRequestDAO = new BloodRequestDAO();

                DonationRequest donationRequest = donationRequestDAO.getRequestById(donationID);
                if (donationRequest != null && Feed.getCurrentUser().getUniqueId() == donationRequest.getCreatorUser().getUniqueId()) {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreatorsRequestPage.fxml"));
                    Scene newScene = new Scene(fxmlLoader.load(), 1200, 800);
                    Stage primaryStage = getPrimaryStage();
                    if (primaryStage != null) {
                        primaryStage.setTitle("Request");
                        primaryStage.setScene(newScene);
                    }
                    System.out.println("YİĞİTYİĞİT");
                }
                else{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DonationPageSeenByUser.fxml"));
                    Scene newScene = new Scene(fxmlLoader.load(), 1200, 800);
                    Stage primaryStage = getPrimaryStage();
                    if (primaryStage != null) {
                        primaryStage.setTitle("Request");
                        primaryStage.setScene(newScene);
                    }

                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }

            notificationStage.close();
        });

        dismissButton.setOnAction(e -> notificationStage.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(messageLabel, goToNotificationButton, dismissButton);

        Scene scene = new Scene(layout, 300, 150);
        notificationStage.setScene(scene);
        notificationStage.showAndWait();
    }

    private Stage getPrimaryStage() {
        return (Stage) triggerButton.getScene().getWindow();
    }
}