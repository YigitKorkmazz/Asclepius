package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class DonationRequest {
    // Instance variables
    private static int uniqueId;
    private User creatorUser;
    private String nameOfPatient;
    private String phoneNumberAssc;
    private String address;
    private City city;  // Using enum type for city
    private BloodType bloodType;  // Using enum type for blood type
    private TransportationAssist transportationAssist;  // Using enum type for transportation assist
    private MoneyAssist moneyAssist;  // Using enum type for money assist
    private ArrayList<User> usersAcceptedList;

    // Enums for city, blood type, transportation assist, and money assist
    public enum City {
        Ankara, Istanbul, Izmir
    }

    public enum BloodType {
        ABRHPositive, ARHPositive, ARHNegative, BRHPositive, ZeroRHPositive, ABRHNegative, BRHNegative, ZeroRHNegative
    }

    public enum TransportationAssist {
        Yes, No;
    }

    public enum MoneyAssist {
        ZERO, FIFTY, HUNDRED
    }

    public DonationRequest(User creatorName, String phoneNumberAssc, String address,
                           City city, BloodType bloodType, TransportationAssist transportationAssist,
                           MoneyAssist moneyAssist, List<User> usersAcceptedList, String nameOfPatient) {
        this.uniqueId++;
        this.creatorUser = creatorName;
        this.phoneNumberAssc = phoneNumberAssc;
        this.address = address;
        this.city = city != null ? city : City.Ankara; // Default city if null
        this.bloodType = bloodType;
        this.transportationAssist = transportationAssist != null ? transportationAssist : TransportationAssist.No; // Default assist if null
        this.moneyAssist = moneyAssist != null ? moneyAssist : MoneyAssist.ZERO; // Default money assist if null
        this.usersAcceptedList = new ArrayList<>(usersAcceptedList);
        this.nameOfPatient = nameOfPatient;
    }

    // @TO-DO: some of the setters may be redundant. @umutcaginozcan
    public int getUniqueId() {
        return uniqueId;
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
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

    public String getNameOfPatient() {
        return nameOfPatient;
    }

    public void setNameOfPatient(String nameOfPatient) {
        this.nameOfPatient = nameOfPatient;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public TransportationAssist getTransportationAssist() {
       if (transportationAssist == null)
       {
           transportationAssist = TransportationAssist.No;
       }
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
            case "ISTANBUL": return City.Istanbul;
            case "İSTANBUL": return City.Istanbul;
            case "Izmir": return City.Izmir;
            case "IZMIR": return City.Izmir;
            case "İZMIR": return City.Izmir;
            case "Ankara": return City.Ankara;
            case "ANKARA": return City.Ankara;
            default: return null;
        }
    }

    public static DonationRequest.TransportationAssist convertStringTypeToEnumForTransportationAsist(String type) {
        switch (type) {
            case "Yes": return TransportationAssist.Yes;
            case "YES": return  TransportationAssist.Yes;
            case "No" : return TransportationAssist.No;
            case "NO" : return TransportationAssist.No;


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
