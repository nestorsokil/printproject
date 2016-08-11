package com.myproject.sample.config;

public interface ApplicationConfigurator {
    Boolean useImScaler();

    String getImageMagickHome();

    String getUserStoragePath();

    String getTempStoragePath();

    String getJbossHome();
}
