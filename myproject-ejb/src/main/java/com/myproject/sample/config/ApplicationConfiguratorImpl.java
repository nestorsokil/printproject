package com.myproject.sample.config;

import com.myproject.sample.dao.StorageDao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class ApplicationConfiguratorImpl implements ApplicationConfigurator {
    @Inject private StorageDao storageDao;

    private boolean useIm;

    private boolean generatePdf;

    private Properties globalProperties;

    @PostConstruct private void init(){
        globalProperties = new Properties();
        globalProperties.setProperty(AppProperty.JBOSS_HOME.name(), System.getenv("JBOSS_HOME"));

        String userStoragePath = storageDao.findById("storage_user").getPath();
        globalProperties.setProperty(AppProperty.USER_STORAGE_PATH.name(), userStoragePath);

        String tempStoragePath = storageDao.findById("storage_temp").getPath();
        globalProperties.setProperty(AppProperty.TEMP_STORAGE_PATH.name(), tempStoragePath);

        String jbossConfig = System.getProperty("jboss.server.config.dir");
        globalProperties.setProperty(AppProperty.JBOSS_CONFIG_DIR.name(), jbossConfig);

        String path =  jbossConfig + File.separator + "application.properties";
        Properties properties = new Properties();
        String imageMagickHome = "";
        try (InputStream in = new FileInputStream(path)){
            properties.load(in);
            imageMagickHome = properties.getProperty("im.home");
            useIm = Boolean.parseBoolean(properties.getProperty("use.im.scaler"));
            generatePdf = Boolean.parseBoolean(properties.getProperty("generate.pdf"));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        globalProperties.setProperty(AppProperty.IM_HOME.name(), imageMagickHome);
    }

    public String getProperty(AppProperty property){
        return globalProperties.getProperty(property.name());
    }

    @Override public ScalerType getScalerBeanQualifier() {
        return useIm ? ScalerType.IMAGE_MAGICK_SCALER : ScalerType.GRAPHICS_2D_SCALER;
    }

    @Override public GeneratedSourcesType getCanvasBeanQualifier() {
        return generatePdf ? GeneratedSourcesType.PDF : GeneratedSourcesType.PNG;
    }

    @Override public void invalidate() {
        init();
    }
}
