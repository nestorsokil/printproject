package com.myproject.sample.service;

import com.myproject.sample.model.User;

public interface UserService extends GenericService<User> {
    User findByUsername(String username);
}
