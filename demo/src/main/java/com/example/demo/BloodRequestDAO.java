package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {
    private String jdbcURL = "jdbc:mysql://myFirstProject_willingmen:c45cce85f4f1feff87e1055d85bd97153672d7bb@tzq.h.filess.io:3307/myFirstProject_willingmen";
    private String jdbcUsername = "myFirstProject_willingmen";
    private String jdbcPassword = "c45cce85f4f1feff87e1055d85bd97153672d7bb";

    private static final String SELECT_ALL_BLOOD_REQUESTS = "SELECT * FROM blood_requests";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<DonationRequest> listAllBloodRequests() {
        List<DonationRequest> bloodRequests = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BLOOD_REQUESTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int uniqueId = rs.getInt("unique_id");
                // Assuming User has a constructor that accepts a string for the name
                User patientName = new User(
                        rs.getString("patient_type"),
                        rs.getString("patient_name"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("city")
                );
                String phoneNumberAssc = rs.getString("phone_number");
                String address = rs.getString("address");
                // Using valueOf to convert String to Enum
                DonationRequest.City city = DonationRequest.City.valueOf(rs.getString("city"));
                DonationRequest.BloodType bloodType = DonationRequest.BloodType.valueOf(rs.getString("blood_type"));
                DonationRequest.TransportationAssist transportationAssist = DonationRequest.TransportationAssist.valueOf(rs.getString("transportation_assist"));
                DonationRequest.MoneyAssist moneyAssist = DonationRequest.MoneyAssist.valueOf(rs.getString("money_assist"));
                ArrayList<User> usersAcceptedList = new ArrayList<>(); // More complex fetching logic might be needed here
                bloodRequests.add(new DonationRequest(uniqueId, patientName, phoneNumberAssc, address, city, bloodType, transportationAssist, moneyAssist, usersAcceptedList));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloodRequests;
    }
}
