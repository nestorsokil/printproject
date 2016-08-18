package com.myproject.sample.canvas;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.ProjectXml;
import com.myproject.sample.xmlmodel.TextXml;

import java.io.File;

public interface ICanvas {
    void init(ProjectXml projectXml, File projectFolder);

    void drawImage(ImageXml imageXml) throws UnsuccessfulProcessingException;

    void drawText(TextXml textXml);

    void generate(File folder) throws UnsuccessfulProcessingException;
}
