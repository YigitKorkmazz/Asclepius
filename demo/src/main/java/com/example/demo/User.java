package com.example.demo;

import java.util.ArrayList;

public class User extends UserController{
    //instance variables
    private int uniqueId;
    private String name;
    private String phoneNumber;
    private String password;
    private enum BloodType
    {
        ABRHPositive,
        ARHNegative,
        ARHPositive,
        BRHPositive,
        ZeroRHPositive,
        ABRHNegative,
        BRHNegative,
        ZeroRHNegative
    }
    private BloodType bloodType;

    private enum city{}
    private ArrayList<com.example.demo.DonationRequest> ownDonationRequest;
    private ArrayList<com.example.demo.DonationRequest> acceptedDonationRequests;

    public User (String type, String name, String phoneNumber, String password, String city)
    {
        this.bloodType = convertStringTypeToEnum(type);
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
    }

    public BloodType convertStringTypeToEnum (String type)
    {
        if (type.equals("ABRH+"))
        {
            return BloodType.ABRHPositive;
        }
        else if (type.equals("ABRH-"))
        {
            return BloodType.ABRHNegative;
        }
        else if (type.equals("ARH+"))
        {
            return BloodType.ARHPositive;
        }
        else if (type.equals("ARH-"))
        {
            return BloodType.ARHNegative;
        }
        else if (type.equals("BRH+"))
        {
            return BloodType.BRHPositive;
        }
        else if (type.equals("BRH-"))
        {
            return BloodType.BRHNegative;
        }
        else if (type.equals("0RH+"))
        {
            return BloodType.ZeroRHPositive;
        }
        else if (type.equals("0RH-"))
        {
            return BloodType.ZeroRHNegative;
        }

        return null;
    }

    public void addDonation (com.example.demo.DonationRequest request)
    {
        //TODO
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BloodType getBloodType() {
        return bloodType;
    }
}
