package com.myproject.sample.dao;


import javax.ejb.Stateless;
import java.util.List;

public interface GenericDao<T> {

    void create(T entity);

    T findById(Object id);

    void delete(T entity);

    T update(T entity);

    List<T> findAll();
}
