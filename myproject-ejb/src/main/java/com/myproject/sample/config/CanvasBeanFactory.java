package com.myproject.sample.config;

import com.myproject.sample.canvas.FactoryProducedCanvas;
import com.myproject.sample.canvas.ICanvas;
import com.myproject.sample.canvas.PdfCanvas;
import com.myproject.sample.canvas.PngCanvas;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class CanvasBeanFactory {
    @Inject private ApplicationConfigurator applicationConfigurator;

    @Produces
    @FactoryProducedCanvas
    public ICanvas getCanvasBean(@New PngCanvas png, @New PdfCanvas pdf){
        if(applicationConfigurator.getCanvasBeanQualifier().equals(GeneratedSourcesType.PDF))
            return pdf;
        return png;
    }
}
