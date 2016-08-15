package com.myproject.sample.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Project {
    @Id
    @GenericGenerator(name="uuid_gen", strategy = "com.myproject.sample.model.UUIDGenerator")
    @GeneratedValue(generator = "uuid_gen")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

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
