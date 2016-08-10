package com.myproject.sample.service;

import com.myproject.sample.model.Project;

public class ProjectServiceImpl extends GenericServiceImpl<Project> implements ProjectService {

    @Override
    public void create(Project entity){
        //TODO: save project to user's storage
    }
}
