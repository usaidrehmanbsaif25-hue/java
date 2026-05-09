package com.srms.dao;

import com.srms.model.User;

public interface UserDAO {
    User getUserByUsername(String username);
}
