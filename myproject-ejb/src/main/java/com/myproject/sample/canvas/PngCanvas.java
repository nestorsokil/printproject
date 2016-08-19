package com.myproject.sample.canvas;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.ProjectXml;
import com.myproject.sample.xmlmodel.TextXml;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngCanvas extends AbstractCanvas implements ICanvas{

    private Graphics2D graphics;
    private BufferedImage image;

    @Override public void init(ProjectXml projectXml, File projectFolder) {
        this.projectFolder = projectFolder;
        int width = projectXml.getWidth(), height = projectXml.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        super.init(projectXml, projectFolder);
    }

    @Override public void generate(File folder) throws UnsuccessfulProcessingException {
        File processedPng = new File(folder, "processed.png");
        File thumbnail = new File(folder, "thumbnail.png");
        try {
            ImageIO.write(image, "png", processedPng);
            imageScaler.scale(processedPng, thumbnail, 200, 200);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException("Could not save result");
        }
    }

    @Override public void drawImage(ImageXml imageXml) throws UnsuccessfulProcessingException{
        File tempImage = scale(imageXml);
        try {
            BufferedImage imgBuff = ImageIO.read(tempImage);
            tempImage.delete();
            graphics.drawImage(imgBuff, imageXml.getX(), imageXml.getY(), null);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException("Could not find scaled image");
        }
    }

    @Override public void drawText(TextXml textXml) {
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, textXml.getFontSize()));
        graphics.drawString(textXml.getValue(), textXml.getX(), textXml.getY());
    }
}
