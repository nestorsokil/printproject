package com.myproject.sample.config;

import com.myproject.sample.imgprocess.Graphics2DImageScaler;
import com.myproject.sample.imgprocess.IMagickImageScaler;
import com.myproject.sample.imgprocess.ImageScaler;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ScalerBeanFactory {
    @Inject private ApplicationConfigurator applicationConfigurator;

    @Produces
    public ImageScaler getScalerBean(){
        if(applicationConfigurator.getScalerBeanQualifier().equals(ScalerType.IMAGE_MAGICK_SCALER))
            return new IMagickImageScaler(applicationConfigurator);
        return new Graphics2DImageScaler();
    }
}
