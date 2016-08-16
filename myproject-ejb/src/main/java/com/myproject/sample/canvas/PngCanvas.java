package com.myproject.sample.canvas;

import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.TextXml;

import javax.imageio.ImageIO;
import javax.inject.Named;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Named("PNG")
public class PngCanvas implements AbstractCanvas {
    private Graphics2D graphics;

    public PngCanvas(Graphics2D graphics) {
        this.graphics = graphics;
    }

    @Override public void drawImage(ImageXml imageXml, File tempImage) throws IOException {
        BufferedImage imgBuff = createBuffFromFile(tempImage);
        tempImage.delete();
        graphics.drawImage(imgBuff, imageXml.getX(), imageXml.getY(), null);
    }

    private BufferedImage createBuffFromFile(File path) throws IOException {
        return ImageIO.read(path);
    }

    @Override public void drawText(TextXml textXml) {
        graphics.drawString(textXml.getValue(), textXml.getX(), textXml.getY());
    }
}
