package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {
    protected String jdbcURL = "jdbc:mysql://myFirstProject_willingmen:c45cce85f4f1feff87e1055d85bd97153672d7bb@tzq.h.filess.io:3307/myFirstProject_willingmen";
    protected String jdbcUsername = "myFirstProject_willingmen";
    protected String jdbcPassword = "c45cce85f4f1feff87e1055d85bd97153672d7bb";

    private static final String SELECT_ALL_BLOOD_REQUESTS = "SELECT * FROM Donations";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE unique_id = ?";
    private static final String INSERT_SQL = "INSERT INTO Donations (User_id, patient_name, phone_number, address, city, bloodType, transportationAssist, moneyAssist) VALUES (?, ?, ?,?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Donations SET User_id = ?, patient_name = ?, phone_number = ?, address = ?, city = ?, bloodType = ?, transportationAssist = ?, moneyAssist = ? WHERE donation_id = ?";
    private static final String DELETE_SQL = "DELETE FROM Donations WHERE donation_id = ?";


    public BloodRequestDAO() {}

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
                int uniqueId = rs.getInt("User_id");
                // Assuming User has a constructor that accepts a string for the name
                User patientName = new User(
                        rs.getString("bloodType"),
                        rs.getString("patient_name"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("city")
                );
                String phoneNumberAssc = rs.getString("phone_number");
                String address = rs.getString("address");
                // Using valueOf to convert String to Enum
                DonationRequest.City city = DonationRequest.City.valueOf(rs.getString("city"));
                DonationRequest.BloodType bloodType = DonationRequest.BloodType.valueOf(rs.getString("bloodType"));
                DonationRequest.TransportationAssist transportationAssist = DonationRequest.TransportationAssist.valueOf(rs.getString("transportation_assist"));
                DonationRequest.MoneyAssist moneyAssist = DonationRequest.MoneyAssist.valueOf(rs.getString("money_assist"));
                ArrayList<User> usersAcceptedList = new ArrayList<>(); // More complex fetching logic might be needed here
                bloodRequests.add(new DonationRequest(patientName, phoneNumberAssc, address, city, bloodType, transportationAssist, moneyAssist, usersAcceptedList));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloodRequests;
    }

    private User getUserById(int userId) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String type = rs.getString("bloodType");
                String name = rs.getString("patient_name");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("password");
                String city = rs.getString("city");
                user = new User(type, name, phoneNumber, password, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insertDonationRequest(DonationRequest request) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setInt(1, request.getPatientName().getUniqueId()); // Assuming getPatientName() returns a User object which has a getUniqueId()
            preparedStatement.setString(2, request.getPatientName().getName());
            preparedStatement.setString(3, request.getPatientName().getPhoneNumber());
            preparedStatement.setString(4, request.getAddress());
            preparedStatement.setString(5, request.getCity().toString()); // Assuming City is an enum and you store its string representation
            preparedStatement.setString(6, request.getBloodType().toString()); // Same assumption for BloodType
            preparedStatement.setString(7, request.getTransportationAssist().toString()); // And TransportationAssist
            preparedStatement.setString(8, request.getMoneyAssist().toString()); // And MoneyAssist

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Donation request was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while inserting the donation request.");
            e.printStackTrace();
        }
    }

    public void updateDonationRequest(DonationRequest request) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setInt(1, request.getPatientName().getUniqueId());
            preparedStatement.setString(2, request.getPatientName().getPhoneNumber());
            preparedStatement.setString(3, request.getAddress());
            preparedStatement.setString(4, request.getCity().toString());
            preparedStatement.setString(5, request.getBloodType().toString());
            preparedStatement.setString(6, request.getTransportationAssist().toString());
            preparedStatement.setString(7, request.getMoneyAssist().toString());
            preparedStatement.setInt(8, request.getUniqueId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Updating the donation request failed, no rows affected.");
                throw new SQLException("Updating the donation request failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the donation request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDonationRequest(int requestId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, requestId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Donation request was deleted successfully!");
            } else {
                System.out.println("No donation request was found.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the donation request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
