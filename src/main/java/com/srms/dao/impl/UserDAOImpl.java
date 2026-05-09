package com.srms.dao.impl;

import com.srms.dao.UserDAO;
import com.srms.database.DBConnection;
import com.srms.model.Admin;
import com.srms.model.Teacher;
import com.srms.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    User user = "ADMIN".equalsIgnoreCase(role) ? new Admin() : new Teacher();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(role);
                    return user;
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get user", exception);
        }
        return null;
    }
}
