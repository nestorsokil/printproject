package com.myproject.sample.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDaoImpl<T> implements GenericDao<T>{

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl(){
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    public void create(T entity){
        em.persist(entity);
    }

    public T findById(Object id){
        return em.find(entityClass, id);
    }

    public void delete(T entity){
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public T update(T entity){
        return em.merge(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(){
        return em.createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

}
