package com.myproject.sample.service;

import com.myproject.sample.dao.StorageDao;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.Storage;
import com.myproject.sample.model.User;
import com.myproject.sample.processor.ProjectProcessor;

import javax.inject.Inject;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class StorageServiceImpl implements StorageService{

    @Inject private StorageDao storageDao;

    @Inject private ProjectService projectService;

    @Inject private ProjectProcessor processor;

    @Override
    public String saveProject(User uploader, InputStream fileStream, String fileName) throws IOException {
        Storage userStorage = getStorageById("storage_user");
        Project project = new Project();
        project.setName(fileName.substring(0, fileName.lastIndexOf(".")));
        project.setUser(uploader);
        project.setStorage(userStorage);
        project = projectService.update(project);

        String projectPath = userStorage.getPath() + File.separator + project.getId();
        File projectFolder = new File(projectPath);
        if(!projectFolder.mkdir())
            throw new IOException("Could not create folder");
        try {
            unzipProject(fileStream, projectPath);
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

    private void unzipProject(InputStream fileStream, String projectPath) throws IOException{
        try (ZipInputStream zipIn = new ZipInputStream(fileStream)){

            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = projectPath + File.separator + entry.getName();
                extractFile(zipIn, filePath);
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException{
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
