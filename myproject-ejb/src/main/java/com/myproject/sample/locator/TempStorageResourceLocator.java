package com.myproject.sample.locator;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;

import javax.inject.Inject;
import java.io.File;

public class TempStorageResourceLocator {
    @Inject private ApplicationConfigurator applicationConfigurator;

    public File locate(String filename){
        return new File(applicationConfigurator.getProperty(AppProperty.TEMP_STORAGE_PATH) + File.separator + filename);
    }
}
