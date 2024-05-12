package com.example.demo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class Notification {
    public enum Type {

        TAGGED,
        REQUEST_ACCEPTED,
        URGENT_DONATION_REQUEST
    }
    private String message;
    public void sendNotification(User user, Type type) {
        switch (type) {
            case TAGGED:
                message = "You have been tagged in a donation request.";
                break;
            case REQUEST_ACCEPTED:
                message = "Your donation request has been accepted.";
                break;
            case URGENT_DONATION_REQUEST:
                message = "There is an urgent donation request that matches your blood type.";
                break;
        }
// TODO: Implement logic to send the notification to the user
// You can use the user's notification preferences and send the notification accordingly
// For example, you can update the user's notification text in the database
// and check for notifications periodically to send them as OS native notifications
    }
}


