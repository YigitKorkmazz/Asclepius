package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class DonationRequest {
    // Instance variables
    private static int uniqueId;
    private User patientName;
    private String phoneNumberAssc;
    private String address;
    private City city;  // Using enum type for city
    private BloodType bloodType;  // Using enum type for blood type
    private TransportationAssist transportationAssist;  // Using enum type for transportation assist
    private MoneyAssist moneyAssist;  // Using enum type for money assist
    private ArrayList<User> usersAcceptedList;

    // Enums for city, blood type, transportation assist, and money assist
    public enum City {
        Ankara,
        Istanbul,
        Izmir
    }

    public enum BloodType {
        ABRHPositive,
        ARHPositive,
        ARHNegative,
        BRHPositive,
        ZeroRHPositive,
        ABRHNegative,
        BRHNegative,
        ZeroRHNegative
    }

    public enum TransportationAssist {
        Yes,No;
    }

    public enum MoneyAssist {
        ZERO, FIFTY, HUNDRED
    }

    // Constructor
    public DonationRequest(User patientName, String phoneNumberAssc, String address,
                           City city, BloodType bloodType, TransportationAssist transportationAssist,
                           MoneyAssist moneyAssist, List<User> usersAcceptedList) {
        this.uniqueId++;
        this.patientName = patientName;
        this.phoneNumberAssc = phoneNumberAssc;
        this.address = address;
        this.city = city;
        this.bloodType = bloodType;
        this.transportationAssist = transportationAssist;
        this.moneyAssist = moneyAssist;
        this.usersAcceptedList = new ArrayList<>(usersAcceptedList);
    }

    // @TO-DO: some of the setters may be redundant. @umutcaginozcan
    public int getUniqueId() {
        return uniqueId;
    }

    public User getPatientName() {
        return patientName;
    }

    public void setPatientName(User patientName) {
        this.patientName = patientName;
    }

    public ArrayList<User> getUsersAcceptedList() {
        return usersAcceptedList;
    }

    public void setUsersAcceptedList(ArrayList<User> usersAcceptedList) {
        this.usersAcceptedList = usersAcceptedList;
    }

    public MoneyAssist getMoneyAssist() {
        return moneyAssist;
    }

    public void setMoneyAssist(MoneyAssist moneyAssist) {
        this.moneyAssist = moneyAssist;
    }

    public String getPhoneNumberAssc() {
        return phoneNumberAssc;
    }

    public void setPhoneNumberAssc(String phoneNumberAssc) {
        this.phoneNumberAssc = phoneNumberAssc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public TransportationAssist getTransportationAssist() {
        return transportationAssist;
    }

    public void setTransportationAssist(TransportationAssist transportationAssist) {
        this.transportationAssist = transportationAssist;
    }

    public static MoneyAssist convertStringToMoneyAssist (String amount)
    {
        switch (amount)
        {
            case "0 USD" : return MoneyAssist.ZERO;
            case "50 USD" : return MoneyAssist.FIFTY;
            case "100 USD" : return MoneyAssist.HUNDRED;
            default: return null;
        }
    }

    public String getMoneyAssistAsString ()
    {
        switch (this.moneyAssist)
        {
            case ZERO : return "0 usd";
            case FIFTY: return "50 usd";
            case HUNDRED: return "100 usd";
            default: return  null;
        }
    }

    public static DonationRequest.BloodType convertStringTypeToEnum(String type) {
        switch (type) {
            case "ABRH+": return DonationRequest.BloodType.ABRHPositive;
            case "ABRH-": return DonationRequest.BloodType.ABRHNegative;
            case "ARH+": return DonationRequest.BloodType.ARHPositive;
            case "ARH-": return DonationRequest.BloodType.ARHNegative;
            case "BRH+": return DonationRequest.BloodType.BRHPositive;
            case "BRH-": return DonationRequest.BloodType.BRHNegative;
            case "0RH+": return DonationRequest.BloodType.ZeroRHPositive;
            case "0RH-": return DonationRequest.BloodType.ZeroRHNegative;
            default: return null;
        }
    }

    public static DonationRequest.City convertStringTypeToEnumForCity(String type) {
        switch (type) {
            case "Istanbul": return City.Istanbul;
            case "Izmir": return City.Izmir;
            case "Ankara": return City.Ankara;

            default: return null;
        }
    }

    public static DonationRequest.TransportationAssist convertStringTypeToEnumForTransportationAsist(String type) {
        switch (type) {
            case "Yes": return TransportationAssist.Yes;
            case "No" : return TransportationAssist.No;


            default: return null;
        }
    }

    //enum to string for city
    public String getCityAsString()
    {
        switch (this.city) {
            case Istanbul : return "Istanbul";
            case Izmir: return "Izmir";
            case Ankara: return "Ankara";
            default: return "Unknown";
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
}
