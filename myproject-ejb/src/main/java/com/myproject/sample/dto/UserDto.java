package com.myproject.sample.dto;

import com.myproject.sample.model.User;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String role;

    public UserDto(User user){
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();

        role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
