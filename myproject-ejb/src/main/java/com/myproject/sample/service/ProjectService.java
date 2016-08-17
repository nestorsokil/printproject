package com.myproject.sample.service;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.User;

import java.io.InputStream;

public interface ProjectService extends GenericService<Project> {
    String saveProject(User uploader, InputStream fileStream, String fileName)
            throws UnsuccessfulProcessingException;
}
