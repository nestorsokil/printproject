package com.myproject.sample.dao;

import com.myproject.sample.model.User;

import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Stateless
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{
    @Override
    public User update(User entity){
        String hashed = org.jboss.crypto.CryptoUtil.createPasswordHash(
                "MD5", "BASE64", "UTF-8", null, entity.getPassword());

        entity.setPassword(hashed);
        return super.update(entity);
    }
}
