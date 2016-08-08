package com.myproject.sample.dao;


public interface GenericDao<T> {

    void create(T entity);

    T findById(Object id);

    void delete(T entity);

    T update(T entity);
}
