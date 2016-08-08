package com.myproject.sample.rest;

import com.myproject.sample.dao.UserDao;
import com.myproject.sample.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserRestApi {
    @Inject private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll(){
        return userDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id){
        return userDao.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public User addUser(User user){
        return userDao.update(user);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") Long id){
        User toDelete = userDao.findById(id);
        userDao.delete(toDelete);
    }

}
