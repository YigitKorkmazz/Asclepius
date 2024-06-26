package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    private DonationRequest request;
    private BloodRequestDAO bloodRequestDAO;

    private boolean isComingFromMyDonations = false;

    @FXML
    public void goRequestPage()
    {
        bloodRequestDAO = new BloodRequestDAO();
        try{
            Stage stage = (Stage) goRequestButton.getScene().getWindow();
            FXMLLoader fxmlLoader = null;
            System.out.println (request.getCreatorUser().getUniqueId() );
            System.out.println (Feed.getCurrentUser().getUniqueId());
            Scene scene = null;
            if (bloodRequestDAO.getUserByDonationID(request.getUniqueId()).getUniqueId() == Feed.getCurrentUser().getUniqueId())
            {
                fxmlLoader = new FXMLLoader(getClass().getResource("CreatorsRequestPage.fxml"));
                 scene = new Scene(fxmlLoader.load(), 1200, 800);
                DonationRequestScreen donationRequestScreen = fxmlLoader.getController();
                donationRequestScreen.setData(request);
            }
            else
            {
                fxmlLoader = new FXMLLoader(getClass().getResource("DonationPageSeenByUser.fxml"));
                 scene = new Scene(fxmlLoader.load(), 1200, 800);
                DonationPageSeenByUser donationPageSeenByUser = fxmlLoader.getController();
                donationPageSeenByUser.setData(request,isComingFromMyDonations);
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

            stage.setTitle("Donation");
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(DonationRequest request){
        this.request = request;
        bloodTypeRequestLabel.setText (request.getBloodTypeAsString());
        nameAndCityRequestLabel.setText (request.getNameOfPatient() + " " + request.getCityAsString());
        addressRequestLabel.setText (request.getAddress());

        if ( !request.getMoneyAssistAsString().equals("0 usd"))
        {
            moneyAssistRequestLabel.setText(request.getMoneyAssistAsString());
        }
        else
        {
            moneyAssistRequestLabel.setVisible(false);
        }

        if (!request.getTransportationAssist().toString().equals("Yes")){
            transportationHelpLabel.setVisible(false);
        }
    }

    public void setComingFromMyDonations(){
        this.isComingFromMyDonations = true;
    }
}
