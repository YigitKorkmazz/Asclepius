package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {
    protected String jdbcURL = "jdbc:mysql://myFirstProject_willingmen:c45cce85f4f1feff87e1055d85bd97153672d7bb@tzq.h.filess.io:3307/myFirstProject_willingmen";
    protected String jdbcUsername = "myFirstProject_willingmen";
    protected String jdbcPassword = "c45cce85f4f1feff87e1055d85bd97153672d7bb";

    private static final String SELECT_ALL_BLOOD_REQUESTS = "SELECT * FROM Donations";
    private static final String SELECT_DONATION_BY_ID = "SELECT * FROM Donations WHERE donation_id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE User_id = ?";
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
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BLOOD_REQUESTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_id");
                User creator = getUserById(userId);  // Correctly fetching the user associated with the request
                creator.setUniqueId(userId);
                String address = rs.getString("address");
                DonationRequest.City city = DonationRequest.convertStringTypeToEnumForCity(rs.getString("city")); // Direct use of enum as stored in DB
                DonationRequest.BloodType bloodType = DonationRequest.convertStringTypeToEnum(rs.getString("bloodType")); // Converting string to enum
                DonationRequest.TransportationAssist transportationAssist = DonationRequest.convertStringTypeToEnumForTransportationAsist(rs.getString("transportationAssist"));
                DonationRequest.MoneyAssist moneyAssist = DonationRequest.convertStringToMoneyAssist(rs.getString("moneyAssist")); // Converting money assist
                String patientName = rs.getString("patient_name");
                List<User> usersAcceptedList = new ArrayList<>(); // Placeholder for accepted users list
                bloodRequests.add(getRequestById(rs.getInt("donation_id"),creator));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bloodRequests;
    }


    private User getUserById (int userId) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String type = rs.getString("blood_type");
                String name = rs.getString("Name");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("userPassword");
                String city = rs.getString("city");
                user = new User(type, name, phoneNumber, password, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public DonationRequest getRequestById (int donation_id, User user) {
        DonationRequest request = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DONATION_BY_ID);) {
            preparedStatement.setInt(1, donation_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String type = rs.getString("bloodType");
                String nameOfPatient = rs.getString("patient_name");
                String phoneNumber = rs.getString("phone_number");
                String city = rs.getString("city");
                String transportationAssist = rs.getString("transportationAssist");
                String moneyAssist = rs.getString ("moneyAssist");
                String address = rs.getString ("address");
                request = new DonationRequest(user, phoneNumber, address,DonationRequest.convertStringTypeToEnumForCity(city), DonationRequest.convertStringTypeToEnum(type), DonationRequest.convertStringTypeToEnumForTransportationAsist(transportationAssist), DonationRequest.convertStringToMoneyAssist(moneyAssist), new ArrayList<User>(), nameOfPatient);
                request.setUniqueId (donation_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void insertDonationRequest(DonationRequest request) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setInt(1, request.getCreatorUser().getUniqueId());
            preparedStatement.setString(2, request.getNameOfPatient());
            preparedStatement.setString(3, request.getPhoneNumberAssc());
            preparedStatement.setString(4, request.getAddress());
            preparedStatement.setString(5, request.getCityAsString());
            preparedStatement.setString(6, request.getBloodTypeAsString());
            preparedStatement.setString(7, request.getTransportationAssist().toString());
            preparedStatement.setString(8, request.getMoneyAssistAsString());

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

            preparedStatement.setInt(1, request.getCreatorUser().getUniqueId());
            preparedStatement.setString(2, request.getCreatorUser().getPhoneNumber());
            preparedStatement.setString(3, request.getAddress());
            preparedStatement.setString(4, request.getCityAsString());
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {

            preparedStatement.setInt(1, requestId);
            System.out.println("REQUESTTTTTT" + requestId);
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
