package com.loginmodulejwt.service;

import com.loginmodulejwt.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);
    User get(Long id);
    List<User> list();
}
