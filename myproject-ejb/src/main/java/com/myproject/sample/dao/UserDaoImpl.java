package com.myproject.sample.dao;

import com.myproject.sample.model.User;

import javax.ejb.Stateless;

@Stateless
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{
}
