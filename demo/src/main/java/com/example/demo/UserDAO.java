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
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
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
            preparedStatement.setString(1, String.valueOf(user.getBloodType()));
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
}
