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

    private String imageMagickHome;

    private String userStoragePath;

    private String tempStoragePath;

    private String jbossConfig;

    private Boolean useIm;

    @PostConstruct private void init(){
        userStoragePath = storageDao.findById("storage_user").getPath();
        tempStoragePath = storageDao.findById("storage_temp").getPath();

        jbossConfig = System.getProperty("jboss.server.config.dir");

        String path =  jbossConfig + File.separator + "application.properties";
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(path)){
            properties.load(in);
            imageMagickHome = properties.getProperty("im.home");
            useIm = Boolean.parseBoolean(properties.getProperty("use.im.scaler"));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String getProperty(AppProperty property){
        switch (property){
            case IM_HOME: return imageMagickHome;
            case JBOSS_HOME: return System.getenv("JBOSS_HOME");
            case JBOSS_CONFIG_DIR: return jbossConfig;
            case USER_STORAGE_PATH: return userStoragePath;
            case TEMP_STORAGE_PATH: return tempStoragePath;
        }

        throw new RuntimeException();
    }

    @Override public ScalerType getScalerBeanQualifier() {
        if(useIm){
            return ScalerType.IMAGE_MAGICK_SCALER;
        }
        return ScalerType.GRAPHICS_2D_SCALER;
    }
}
