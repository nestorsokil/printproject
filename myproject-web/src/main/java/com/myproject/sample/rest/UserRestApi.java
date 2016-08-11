package com.myproject.sample.rest;

import com.myproject.sample.dto.UserDto;
import com.myproject.sample.model.User;
import com.myproject.sample.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserRestApi {
    @Inject private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getAll(){
        List<UserDto> result = new ArrayList<>();
        for(User u: userService.findAll()){
            result.add(new UserDto(u));
        }
        return result;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUser(@PathParam("id") Long id){
        return new UserDto(userService.findById(id));
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
