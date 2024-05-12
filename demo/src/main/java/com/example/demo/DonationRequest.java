package com.example.demo;

import java.util.ArrayList;

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
    public DonationRequest(User patientName, String phoneNumberAssc, String address,
                           City city, BloodType bloodType, TransportationAssist transportationAssist,
                           MoneyAssist moneyAssist, ArrayList<User> usersAcceptedList) {
        this.uniqueId = uniqueId++;
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
}
