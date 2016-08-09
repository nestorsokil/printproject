package com.myproject.sample.rest;

import com.myproject.sample.model.User;
import com.myproject.sample.service.IMagickImageScaler;
import com.myproject.sample.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserRestApi {
    @Inject private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll(){
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id){
        return userService.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public User addUser(User user){
        return userService.update(user);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") Long id){
        User toDelete = userService.findById(id);
        userService.delete(toDelete);
    }

}
