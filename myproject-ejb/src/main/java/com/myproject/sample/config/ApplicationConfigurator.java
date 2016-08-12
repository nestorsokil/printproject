package com.myproject.sample.config;

public interface ApplicationConfigurator {
    String IMAGICK_SCALER_BEAN_NAME = "ImScaler";

    String GRAPHICS2D_SCALER_BEAN_NAME = "graphics2DScaler";

    String getImageMagickHome();

    String getUserStoragePath();

    String getTempStoragePath();

    String getJbossHome();

    String getScalerBeanName();
}
