package com.example.demo;

import java.util.ArrayList;

public class User extends UserController{
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
    private ArrayList<com.example.demo.DonationRequest> ownDonationRequest;
    private ArrayList<com.example.demo.DonationRequest> acceptedDonationRequests;

    public void addDonation (com.example.demo.DonationRequest request)
    {
        //TODO
    }
}
