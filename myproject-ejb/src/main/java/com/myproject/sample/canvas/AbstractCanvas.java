package com.myproject.sample.canvas;

import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.TextXml;
import java.io.File;
import java.io.IOException;

public interface AbstractCanvas {

    void drawImage(ImageXml imageXml, File tempImage) throws IOException;

    void drawText(TextXml textXml);
}
