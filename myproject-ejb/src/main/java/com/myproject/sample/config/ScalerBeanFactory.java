package com.myproject.sample.config;

import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.Graphics2DImageScaler;
import com.myproject.sample.imgprocess.IMagickImageScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ScalerBeanFactory {
    @Inject private ApplicationConfigurator applicationConfigurator;

    @Produces
    @FactoryProducedScaler
    public ImageScaler getScalerBean(){
        if(applicationConfigurator.getScalerBeanQualifier().equals(ScalerType.IMAGE_MAGICK_SCALER))
            return new IMagickImageScaler(applicationConfigurator.getProperty(AppProperty.IM_HOME));
        return new Graphics2DImageScaler();
    }
}
