package com.myproject.sample.config;

import com.myproject.sample.dao.StorageDao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguratorImpl implements ApplicationConfigurator {
    @Inject private StorageDao storageDao;

    private String imageMagickHome;

    private String userStoragePath;

    private String tempStoragePath;

    private Boolean useIm;

    @PostConstruct private void init(){
        userStoragePath = storageDao.findById("storage_user").getPath();
        tempStoragePath = storageDao.findById("storage_temp").getPath();

        Properties properties = new Properties();
        String path = "C:/app-properties/application.properties";
        try {
            InputStream in = new FileInputStream(path);
            properties.load(in);
            imageMagickHome = properties.getProperty("im.home");
            useIm = Boolean.parseBoolean(properties.getProperty("use.im.scaler"));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override public String getImageMagickHome() {
        return imageMagickHome;
    }

    @Override public String getUserStoragePath() {
        return userStoragePath;
    }

    @Override public String getTempStoragePath() {
        return tempStoragePath;
    }

    @Override public Boolean useImScaler(){
        return useIm;
    }


}
