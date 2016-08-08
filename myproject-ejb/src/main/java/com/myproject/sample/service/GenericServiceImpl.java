package com.myproject.sample.service;

import com.myproject.sample.dao.GenericDao;

import javax.inject.Inject;
import java.util.List;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
    @Inject
    private GenericDao<T> dao;

    @Override
    public void create(T entity){
        dao.create(entity);
    }

    @Override
    public T findById(Object id){
        return dao.findById(id);
    }

    @Override
    public void delete(T entity){
        dao.delete(entity);
    }

    @Override
    public T update(T entity){
        return dao.update(entity);
    }

    @Override
    public List<T> findAll(){
        return dao.findAll();
    }
}
