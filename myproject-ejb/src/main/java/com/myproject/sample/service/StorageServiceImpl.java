package com.myproject.sample.service;

import com.myproject.sample.Processor;
import com.myproject.sample.dao.StorageDao;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.Storage;
import com.myproject.sample.model.User;
import com.myproject.sample.processor.ProjectProcessor;
import com.myproject.sample.util.ProjectFileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import java.io.*;
import java.util.Calendar;

public class StorageServiceImpl implements StorageService{

    @Inject private StorageDao storageDao;

    @Inject private ProjectService projectService;

    @Inject @Processor("Basic") private ProjectProcessor processor;


    @Override
    public String saveProject(User uploader, InputStream fileStream, String fileName) throws IOException {
        Storage userStorage = getStorageById("storage_user");
        Project project = new Project();
        project.setName(FilenameUtils.getBaseName(fileName) + "("
                + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss") + ")");
        project.setUser(uploader);
        project.setStorage(userStorage);
        project = projectService.update(project);

        String projectPath = userStorage.getPath() + File.separator + project.getId();
        File projectFolder = new File(projectPath);

        if(!projectFolder.mkdir())
            throw new IOException("Could not create folder");

        try {
            ProjectFileUtils.unzipProject(fileStream, projectPath);
        }catch (IOException ioe){
            projectFolder.delete();
            projectService.delete(project);
            throw ioe;
        }

        try {
            processor.process(project);
        }catch (UnsuccessfulProcessingException upe){
            upe.getCause().printStackTrace();
        }
        return projectPath;
    }

    @Override
    public Storage getStorageById(String id){
        return storageDao.findById(id);
    }
}
