package com.myproject.sample.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.*;

@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "uuid_gen", strategy = "com.myproject.sample.model.UUIDGenerator")
    @GeneratedValue(generator = "uuid_gen")
    private String id;

    private String name;

    @ManyToOne
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    private void writeObject(ObjectOutputStream o) throws IOException {
        o.writeObject(id);
        o.writeObject(name);
    }

    private void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        id = (String) o.readObject();
        name = (String) o.readObject();
    }
}