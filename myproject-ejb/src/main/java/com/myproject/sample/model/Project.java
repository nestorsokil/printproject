package com.myproject.sample.model;

import javax.persistence.*;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
