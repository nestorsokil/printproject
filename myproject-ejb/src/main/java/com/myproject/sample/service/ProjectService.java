package com.myproject.sample.service;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.User;

import java.io.InputStream;
import java.util.List;

public interface ProjectService extends GenericService<Project> {
    long countAll();

    long countAllByUser(User user);

    List<Project> getResultsPage(int startIndex, int pageSize);

    List<Project> getResultsPageByUser(int startIndex, int pageSize, User user);

    String saveProject(User uploader, InputStream fileStream, String fileName)
            throws UnsuccessfulProcessingException;
}
