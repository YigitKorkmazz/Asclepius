package com.example.demo;

import java.util.ArrayList;

public class User extends UserController {
    // Instance variables
    private int uniqueId;
    private String name;
    private String phoneNumber;
    private String password;

    // Enum for BloodType
    private enum BloodType {
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

    private enum City {
        Ankara, Istanbul, Izmir
    }
    private City city; // Assuming you need an instance variable for city

    private ArrayList<DonationRequest> ownDonationRequest;
    private ArrayList<DonationRequest> acceptedDonationRequests;

    public User(String type, String name, String phoneNumber, String password, String cityString) {
        this.bloodType = convertStringTypeToEnum(type);
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.city = City.valueOf(cityString); // This assumes cityString is a valid enum name
    }

    public BloodType convertStringTypeToEnum(String type) {
        switch (type) {
            case "ABRH+": return BloodType.ABRHPositive;
            case "ABRH-": return BloodType.ABRHNegative;
            case "ARH+": return BloodType.ARHPositive;
            case "ARH-": return BloodType.ARHNegative;
            case "BRH+": return BloodType.BRHPositive;
            case "BRH-": return BloodType.BRHNegative;
            case "0RH+": return BloodType.ZeroRHPositive;
            case "0RH-": return BloodType.ZeroRHNegative;
            default: return null;
        }
    }

    public String getBloodTypeAsString() {
        switch (this.bloodType) {
            case ABRHPositive: return "ABRH+";
            case ABRHNegative: return "ABRH-";
            case ARHPositive: return "ARH+";
            case ARHNegative: return "ARH-";
            case BRHPositive: return "BRH+";
            case BRHNegative: return "BRH-";
            case ZeroRHPositive: return "0RH+";
            case ZeroRHNegative: return "0RH-";
            default: return "Unknown";
        }
    }

    public String getCityAsString ()
    {
        switch (this.city)
        {
            case Ankara : return "Ankara";
            case Istanbul: return "Istanbul";
            case Izmir: return "Izmir";
            default: return "unknown";
        }
    }

    // Adding getters and setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public City getCity() {
        return city;
    }

    public void setCity(String cityString) {
        this.city = City.valueOf(cityString);
    }

    public void addDonation(DonationRequest request) {
        // Implementation needed based on your application logic
        // Example: this.ownDonationRequest.add(request);
    }
}
