package com.srms.service;

import com.srms.dao.UserDAO;
import com.srms.dao.impl.UserDAOImpl;
import com.srms.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAOImpl();
    private static User currentUser;

    public boolean login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
