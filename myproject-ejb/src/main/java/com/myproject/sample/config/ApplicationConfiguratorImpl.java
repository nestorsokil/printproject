package com.myproject.sample.config;

import com.myproject.sample.dao.StorageDao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguratorImpl implements ApplicationConfigurator {
    @Inject private StorageDao storageDao;

    //TODO: enum
    private String imageMagickHome;

    private String userStoragePath;

    private String tempStoragePath;

    private Boolean useIm;

    @PostConstruct private void init(){
        userStoragePath = storageDao.findById("storage_user").getPath();
        tempStoragePath = storageDao.findById("storage_temp").getPath();

        String sep = File.separator;

        String path = getJbossHome() + sep + "standalone" + sep + "app-properties" + sep + "application.properties";

        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(path)){
            properties.load(in);
            imageMagickHome = properties.getProperty("im.home");
            useIm = Boolean.parseBoolean(properties.getProperty("use.im.scaler"));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override public String getJbossHome(){
        return System.getenv("JBOSS_HOME");
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

    @Override public String getScalerBeanName() {
        if(useIm){
            return ApplicationConfigurator.IMAGICK_SCALER_BEAN_NAME;
        }
        return ApplicationConfigurator.GRAPHICS2D_SCALER_BEAN_NAME;
    }
}
