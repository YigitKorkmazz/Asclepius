package com.example.demo;

import java.util.ArrayList;

public class User {
    //instance variables
    private int uniqueId;
    private String name;
    private String phoneNumber;
    private enum bloodType
    {
        ABRHPositive,
        ARHPositive,
        BRHPositive,
        ZeroRHPositive,
        ABRHNegative,
        BRHNegative,
        ZeroRHNegative
    }

    private enum city{}
    private ArrayList<DonationRequest> ownDonationRequest;
    private ArrayList<DonationRequest> acceptedDonationRequests;

    public void addDonation (DonationRequest request)
    {
        //TODO
    }
}
