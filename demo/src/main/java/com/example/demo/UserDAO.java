package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    protected String jdbcURL = "jdbc:mysql://myFirstProject_willingmen:c45cce85f4f1feff87e1055d85bd97153672d7bb@tzq.h.filess.io:3307/myFirstProject_willingmen";
    protected String jdbcUsername = "myFirstProject_willingmen";
    protected String jdbcPassword = "c45cce85f4f1feff87e1055d85bd97153672d7bb";

    protected static final String SELECT_ALL_USERS = "SELECT * FROM user";
    protected static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE User_id = ?";
    protected static final String INSERT_USER = "INSERT INTO user (blood_type, Name, phone_number, userPassword, city) VALUES (?, ?, ?, ?, ?)";
    protected static final String UPDATE_USER = "UPDATE user SET blood_type = ?, Name = ?, phone_number = ?, userPassword = ?, city = ? WHERE User_id = ?";
    protected static final String DELETE_USER = "DELETE FROM user WHERE User_id = ?";
    protected static final String SELECT_USER_BY_PHONE_AND_PASSWORD = "SELECT * FROM user WHERE phone_number = ? AND userPassword = ?";
    private static final String SELECT_USER_ID_BY_PHONE = "SELECT User_id FROM user WHERE phone_number = ?";
    protected static final String CURRENT_NOTIFICATION = "UPDATE user SET notification = ? WHERE User_id = ?";
    protected static final String SELECT_NOTIFICATION = "Select notification FROM user WHERE User_id = ?";
    private static final String UPDATE_NOTIFICATION_STATUS = "UPDATE user SET receive_notifications = ? WHERE User_id = ?";
    private static final String SELECT_NOTIFICATION_STATUS = "SELECT receive_notifications FROM user WHERE User_id = ?";

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

    public void updateNotificationStatus(int user_id, boolean wantsToReceive) {
        String status = "Yes";
        if (wantsToReceive)
        {
            status = "No";
        }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTIFICATION_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
            System.out.println("set notification status to " + status);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getNotificationStatus(int user_id) {
        boolean notificationStatus = false;
        String notificationColumn = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, user_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                notificationColumn = rs.getString("receive_notifications");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notificationColumn.equals("Yes");
    }

    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getString("blood_type"),
                        rs.getString("Name"),
                        rs.getString("phone_number"),
                        rs.getString("userPassword"),
                        rs.getString("city")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getString("blood_type"),
                        rs.getString("Name"),
                        rs.getString("phone_number"),
                        rs.getString("userPassword"),
                        rs.getString("city")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateNotification(int userId, String message) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CURRENT_NOTIFICATION)) {
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int findUserIdByPhoneNumber(String phoneNumber) {
        int userId = -1; // Use -1 as default to indicate user not found
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ID_BY_PHONE)) {
            preparedStatement.setString(1, phoneNumber);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("User_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }


    public void addUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);) {
            preparedStatement.setString(1, String.valueOf(user.getBloodType()));
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getCity()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);) {
            preparedStatement.setString(1, user.getBloodTypeAsString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getCity()));
            preparedStatement.setInt(6, user.getUniqueId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNotification(int taggedUserId) throws SQLException {
        String notification = ""; // Default to empty if no data is found
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTIFICATION)) {
            preparedStatement.setInt(1, taggedUserId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    notification = rs.getString("notification");
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
                throw e; // Re-throw exception to handle it in calling method or to log it
            }
        } catch (SQLException e) {
            System.out.println("Error getting connection or preparing statement: " + e.getMessage());
            throw e; // Ensure that SQL exceptions are propagated up
        }
        return notification;
    }
}