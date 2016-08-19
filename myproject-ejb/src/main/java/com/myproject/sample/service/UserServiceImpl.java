package com.myproject.sample.service;

import com.myproject.sample.dao.UserDao;
import com.myproject.sample.model.User;

import javax.inject.Inject;

public class UserServiceImpl extends GenericServiceImpl<User> implements UserService{
    @Inject UserDao userDao;

    @Override
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }
}
