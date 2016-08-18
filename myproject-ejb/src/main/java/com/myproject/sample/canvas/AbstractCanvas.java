package com.myproject.sample.canvas;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.ProjectXml;
import com.myproject.sample.xmlmodel.TextXml;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

public abstract class AbstractCanvas implements ICanvas {
    @Inject @FactoryProducedScaler protected ImageScaler imageScaler;

    @Inject protected TempStorageResourceLocator tempLocator;

    protected File projectFolder;

    public void init(ProjectXml projectXml, File projectFolder){
        this.projectFolder = projectFolder;
    }

    abstract public void drawImage(ImageXml imageXml) throws UnsuccessfulProcessingException;

    abstract public void drawText(TextXml textXml);

    abstract public void generate(File folder) throws UnsuccessfulProcessingException;

    protected File scale(ImageXml imageXml) throws UnsuccessfulProcessingException{
        int width = imageXml.getWidth(), height = imageXml.getHeight();
        String imgRef = imageXml.getImageRef();

        File sourceImg = new File(projectFolder, imgRef);
        File targetImg = tempLocator.locate(ProjectFileUtils.makeTempImgName(imgRef, width, height));
        try {
            imageScaler.scale(sourceImg, targetImg, width, height);
        }catch (IOException ioe) {
            ioe.printStackTrace();
            throw new UnsuccessfulProcessingException("Could not scale image");
        }

        return targetImg;
    }
}
