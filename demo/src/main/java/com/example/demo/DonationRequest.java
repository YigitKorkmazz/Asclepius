package com.example.demo;

import java.util.ArrayList;

public class DonationRequest {
    // Instance variables
    private int uniqueId;
    private User patientName;  // Ensure User class is defined elsewhere in your project
    private String phoneNumberAssc;
    private String address;
    private City city;  // Using enum type for city
    private BloodType bloodType;  // Using enum type for blood type
    private TransportationAssist transportationAssist;  // Using enum type for transportation assist
    private MoneyAssist moneyAssist;  // Using enum type for money assist
    private ArrayList<User> usersAcceptedList;

    // Enums for city, blood type, transportation assist, and money assist
    public enum City {
        ANKARA, ISTANBUL, IZMIR // Example cities
    }

    public enum BloodType {
        ABRHPositive,
        ARHPositive,
        BRHPositive,
        ZeroRHPositive,
        ABRHNegative,
        BRHNegative,
        ZeroRHNegative
    }

    public enum TransportationAssist {
        REQUIRED, NOT_REQUIRED // Example values
    }

    public enum MoneyAssist {
        ZERO, FIFTY, HUNDRED
    }

    // Constructor
    public DonationRequest(int uniqueId, User patientName, String phoneNumberAssc, String address,
                           City city, BloodType bloodType, TransportationAssist transportationAssist,
                           MoneyAssist moneyAssist, ArrayList<User> usersAcceptedList) {
        this.uniqueId = uniqueId;
        this.patientName = patientName;
        this.phoneNumberAssc = phoneNumberAssc;
        this.address = address;
        this.city = city;
        this.bloodType = bloodType;
        this.transportationAssist = transportationAssist;
        this.moneyAssist = moneyAssist;
        this.usersAcceptedList = new ArrayList<>(usersAcceptedList);
    }

    // Getters and setters (Add as needed for your functionality)
}
