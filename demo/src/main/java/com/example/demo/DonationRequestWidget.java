package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
public class DonationRequestWidget extends VBox {
    private Label patientNameLabel;
    private Label bloodTypeLabel;
    private Label locationLabel;
    private Label transportationHelpLabel;
    private Label moneyHelpLabel;
    private Button acceptButton;
    private Button retreatButton;
    private Button tagFriendButton;
    public DonationRequestWidget(DonationRequest request) {
    // Initialize UI components and set their values based on the donation request
        patientNameLabel = new Label(request.getPatientName().getName());
        bloodTypeLabel = new Label(request.getBloodType().toString());
        locationLabel = new Label(request.getCity().toString());

        transportationHelpLabel = new Label(request.getTransportationAssist().toString());
        moneyHelpLabel = new Label(request.getMoneyAssist().toString());
        acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> acceptDonation(request));
        retreatButton = new Button("Retreat");
        retreatButton.setOnAction(e -> retreatDonation(request));
        tagFriendButton = new Button("Tag a Friend");
        tagFriendButton.setOnAction(e -> tagFriend(request));
        // Add UI components to the VBox
        getChildren().addAll(patientNameLabel, bloodTypeLabel, locationLabel,
                transportationHelpLabel, moneyHelpLabel, acceptButton, retreatButton, tagFriendButton);
    }
    private void acceptDonation(DonationRequest request) {
// TODO: Implement logic to accept the donation request
    }
    private void retreatDonation(DonationRequest request) {
// TODO: Implement logic to retreat from the donation request
    }
    private void tagFriend(DonationRequest request) {
// TODO: Implement logic to tag a friend for the donation request

    }
}
