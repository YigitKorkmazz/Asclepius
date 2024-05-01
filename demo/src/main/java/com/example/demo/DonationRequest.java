package com.example.demo;

import java.util.ArrayList;

public class DonationRequest {
    //instance variables
    private int uniqueId;
    private User ownerUser;
    private String phoneNumberAssc;
    private String address;
    private enum city{}

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

    private enum transportationAssist {}
    private enum moneyAssist
    {
        ZERO,
        FIFTY,
        HUNDRED
    }
    private ArrayList<User> usersAcceptedList;
}
