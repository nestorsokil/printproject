package com.myproject.sample.canvas;

import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.TextXml;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

public interface AbstractCanvas {

    void drawImage(ImageXml imageXml, File tempImage) throws IOException;

    void drawText(TextXml textXml);
}
