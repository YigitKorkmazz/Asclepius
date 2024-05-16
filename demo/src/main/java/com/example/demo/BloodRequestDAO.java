package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String SELECT_USER_ID_FROM_DONATIONS = "SELECT User_id FROM Donations where donation_id = ?";
    private static final String UPDATE_ACCEPTED_USER = "UPDATE Donations SET acceptedUser = ? Where donation_id = ?";
    private static final String SELECT_ACCEPTED_USERS = "SELECT acceptedUser from Donations where donation_id = ?";

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
                bloodRequests.add(getRequestById(rs.getInt("donation_id")));
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

    public void updateRetreatedDonationForUser(int userId, DonationRequest request) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCEPTED_USER)) {

            // Fetch the current list of accepted users
            List<User> acceptedUsers = request.getUsersAcceptedList();

            // Remove the user with userId from the list of accepted users
            List<User> updatedAcceptedUsers = removeUserFromList(acceptedUsers, userId);

            // Convert the updated list back to a string
            String updatedAcceptedUsersStr = convertUserListToString(updatedAcceptedUsers);

            // Update the database with the new list of accepted users
            preparedStatement.setString(1, updatedAcceptedUsersStr);
            preparedStatement.setInt(2, request.getUniqueId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Retreated from the donation request successfully.");
            } else {
                System.out.println("Failed to retreat from the donation request.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the accepted users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<User> removeUserFromList(List<User> userList, int userId) {
        List<User> updatedList = new ArrayList<>();
        for (User user : userList) {
            if (user.getUniqueId() != userId) {
                updatedList.add(user);
            }
        }
        return updatedList;
    }

    private String convertUserListToString(List<User> userList) {
        StringBuilder sb = new StringBuilder();
        for (User user : userList) {
            sb.append(user.getUniqueId()).append(" ");
        }
        return sb.toString().trim();
    }

    public void deleteUserFromAcceptedList(int donationId, int userId) {
        String acceptedUsers = "";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCEPTED_USERS)) {
            preparedStatement.setInt(1, donationId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                acceptedUsers = rs.getString("acceptedUser");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching existing accepted users: " + e.getMessage());
            e.printStackTrace();
        }

        List<String> userList = new ArrayList<>(Arrays.asList(acceptedUsers.split(" ")));
        userList.remove(String.valueOf(userId));
        String updatedUserList = String.join(" ", userList);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCEPTED_USER)) {
            preparedStatement.setString(1, updatedUserList);
            preparedStatement.setInt(2, donationId);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("User successfully removed from the accepted list.");
            } else {
                System.out.println("Failed to update the accepted list.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the accepted users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateAcceptedUser(int userID, DonationRequest request) {
        // First fetch the current list of accepted users
        String existingAcceptedUsers = "";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCEPTED_USERS)) {
            preparedStatement.setInt(1, request.getUniqueId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                existingAcceptedUsers = rs.getString("acceptedUser");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching existing accepted users: " + e.getMessage());
            e.printStackTrace();
        }

        // Append the new user ID
        String updatedAcceptedUsers = existingAcceptedUsers == null ? String.valueOf(userID) : existingAcceptedUsers + " " + userID;

        // Update the database with the new list of accepted users
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCEPTED_USER)) {
            preparedStatement.setString(1, updatedAcceptedUsers);
            preparedStatement.setInt(2, request.getUniqueId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Accepted users updated successfully.");
            } else {
                System.out.println("Failed to update accepted users.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the accepted users: " + e.getMessage());
            e.printStackTrace();
        }

        // Optionally, add the user to the request's list of accepted users
        request.getUsersAcceptedList().add(getUserById(userID));
    }

    public ArrayList<User> acceptedUsers(DonationRequest request) {
        ArrayList<User> acceptedOnes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCEPTED_USERS)) {
            preparedStatement.setInt(1, request.getUniqueId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String acceptedUsers = rs.getString("acceptedUser");
                if (acceptedUsers != null && !acceptedUsers.isEmpty()) {
                    String[] userIds = acceptedUsers.split(" ");
                    for (String userIdStr : userIds) {
                        try {
                            int userId = Integer.parseInt(userIdStr);
                            User user = getUserById(userId);
                            user.setUniqueId(userId);
                            if (user != null) {
                                acceptedOnes.add(user);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing user ID: " + userIdStr);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception while fetching accepted users: " + e.getMessage());
            e.printStackTrace();
        }

        request.setUsersAcceptedList(acceptedOnes);
        return acceptedOnes;
    }



    
    public User getUserByDonationID (int donation_id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ID_FROM_DONATIONS);) {
            preparedStatement.setInt(1, donation_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("User_id");
                user = getUserById(userId);
                user.setUniqueId(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public DonationRequest getRequestById (int donation_id) {
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
                int UserId = rs.getInt("User_id");
                request = new DonationRequest(getUserById(UserId), phoneNumber, address,DonationRequest.convertStringTypeToEnumForCity(city), DonationRequest.convertStringTypeToEnum(type), DonationRequest.convertStringTypeToEnumForTransportationAsist(transportationAssist), DonationRequest.convertStringToMoneyAssist(moneyAssist), new ArrayList<User>(), nameOfPatient);
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
