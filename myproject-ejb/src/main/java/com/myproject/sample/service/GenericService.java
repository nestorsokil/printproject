package com.myproject.sample.service;

import java.util.List;

public interface GenericService<T> {
    void create(T entity);

    T findById(Object id);

    void delete(T entity);

    T update(T entity);

    List<T> findAll();
}
