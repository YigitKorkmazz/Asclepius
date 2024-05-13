package com.example.demo;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ItemController implements Initializable {

    @FXML
    private Label addressRequestLabel;

    @FXML
    private Label bloodTypeRequestLabel;

    @FXML
    private Button goRequestButton;

    @FXML
    private Button moneyAssistRequestLabel;

    @FXML
    private Label nameAndCityRequestLabel;

    @FXML
    private Button transportationHelpLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(DonationRequest request){

        bloodTypeRequestLabel.setText(request.getBloodTypeAsString());
        nameAndCityRequestLabel.setText(request.getPatientName().getName() + " " + request.getCity().toString());
        addressRequestLabel.setText(request.getAddress());

        if ( !request.getMoneyAssistAsString().equals("0 usd"))
        {
            moneyAssistRequestLabel.setText(request.getMoneyAssistAsString());
        }
        else
        {
            moneyAssistRequestLabel.setVisible(false);
        }

        if ( !request.getTransportationAssist().toString().equals("Yes")){
            transportationHelpLabel.setVisible(false);
        }
    }
}
