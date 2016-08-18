package com.myproject.sample.locator;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;
import com.myproject.sample.model.Project;

import javax.inject.Inject;
import java.io.File;

public class UserStorageResourceLocator {
    @Inject private ApplicationConfigurator appConfig;

    public static String FOLDER_ROOT = "";
    public File locate(Project project, String filename){
        return new File(appConfig.getProperty(AppProperty.USER_STORAGE_PATH)
                + File.separator + project.getId() + File.separator + filename);
    }
}
