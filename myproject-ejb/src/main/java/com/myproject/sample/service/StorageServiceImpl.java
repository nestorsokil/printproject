package com.myproject.sample.service;

import com.myproject.sample.dao.StorageDao;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.Storage;
import com.myproject.sample.model.User;

import javax.inject.Inject;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class StorageServiceImpl implements StorageService{

    @Inject private StorageDao storageDao;

    @Inject private ProjectService projectService;

    @Override
    public String saveProject(User uploader, InputStream fileStream, String fileName) throws IOException {
        Storage userStorage = getStorageById("storage_user");

        Project project = new Project();
        project.setName(fileName.substring(0, fileName.lastIndexOf(".")));
        project.setUser(uploader);
        project.setStorage(userStorage);
        project = projectService.update(project);

        String projectPath = userStorage.getPath() + "\\UUID" + project.getId();
        File projectFolder = new File(projectPath);
        if(!projectFolder.mkdir())
            throw new IOException();

        ZipInputStream zipIn = new ZipInputStream(fileStream);
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = projectPath + File.separator + entry.getName();
            extractFile(zipIn, filePath);
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        return projectPath;
    }

    @Override
    public Storage getStorageById(String id){
        return storageDao.findById(id);
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
