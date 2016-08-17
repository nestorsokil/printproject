package com.myproject.sample.dao;

import com.myproject.sample.model.Project;

import javax.ejb.Stateless;


@Stateless
@SuppressWarnings("unchecked")
public class ProjectDaoImpl extends GenericDaoImpl<Project> implements ProjectDao {
}
