package com.ucbcba.logindemo.services;

import com.ucbcba.logindemo.entities.User;

public interface UserService {
    void save(User user);
    String getLoggedInUserName();
    User getLoggedInUser();
    User findByUsername(String username);
    Iterable<User> listAllUsers();
    User findUserById(Integer id);
}