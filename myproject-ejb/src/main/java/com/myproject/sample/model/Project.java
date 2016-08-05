package com.myproject.sample.model;

import javax.persistence.*;

/**
 * Created by nsokil on 21.07.2016.
 */

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Storage storage;


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
}
