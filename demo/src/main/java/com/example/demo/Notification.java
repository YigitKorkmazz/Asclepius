package com.example.demo;

public class Notification {

    //instance variables
    private enum Type
    {
        TAGGED,
        REQUEST_ACCEPTED,
        URGENT_DONATION_REQUEST
    }

    private String message;

    //methods
    public void sendNotification (User user, Type typeOfNotification)
    {
        //TODO
    }
}
