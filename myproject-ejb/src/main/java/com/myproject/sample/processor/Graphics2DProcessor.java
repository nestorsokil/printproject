package com.myproject.sample.processor;

import com.myproject.sample.config.ScalerBeanFactory;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.xmlmodel.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Graphics2DProcessor implements ProjectProcessor{
    @Inject private XmlParser parser;

    @Inject private ScalerBeanFactory scalerBeanFactory;

    @Inject private TempStorageResourceLocator tempLocator;

    @Inject private UserStorageResourceLocator userLocator;

    @Override public void process(Project project) throws UnsuccessfulProcessingException{

        File xmlScheme = userLocator.locate(project, "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            throw new UnsuccessfulProcessingException(je);
        }

        BufferedImage image = new BufferedImage(projectXml.getWidth(), projectXml.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D canvas = image.createGraphics();
        try {
            processXmlContainer(projectXml, canvas, project);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }

        File processedFile = userLocator.locate(project, "processed" + File.separator + "processed.png");
        processedFile.mkdirs();
        try {
            ImageIO.write(image, "png", processedFile);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }

    private void processXmlContainer(AbstractXmlContainer containerXml, Graphics2D canvas, Project project)
            throws IOException{
        List<ImageXml> images = containerXml.getImages();
        for(ImageXml im : images){
            setElementAbsoluteCoordinates(containerXml, im);
            drawImage(im, canvas, project);
        }

        List<TextXml> texts = containerXml.getTexts();
        for(TextXml tx : texts){
            setElementAbsoluteCoordinates(containerXml, tx);
            drawText(tx, canvas);
        }

        List<BlockXml> blocks = containerXml.getBlocks();
        for(BlockXml bl : blocks){
            setElementAbsoluteCoordinates(containerXml, bl);
            processXmlContainer(bl, canvas, project);
        }
    }

    private void setElementAbsoluteCoordinates(AbstractXmlElement parent, AbstractXmlElement child){
        child.setX(parent.getX() + child.getX());
        child.setY(parent.getY() + child.getY());
    }

    private void drawImage(ImageXml image, Graphics2D projectCanvas, Project project) throws IOException{
        String pathToTempImg = scaleImage(image, project);
        BufferedImage imgBuff = createBuffFromFile(pathToTempImg);
        projectCanvas.drawImage(imgBuff, image.getX(), image.getY(), null);
    }

    private String scaleImage(ImageXml imageXml, Project project){
        int width = imageXml.getWidth(), height = imageXml.getHeight();
        ImageScaler scaler = scalerBeanFactory.getScalerBean();
        String imgRef = imageXml.getImageRef();

        File sourceImg = userLocator.locate(project, imgRef);
        File targetImg = tempLocator.locate(ProjectFileUtils.makeTempImgName(imgRef, width, height));
        scaler.scale(sourceImg, targetImg, width, height);

        return targetImg.getAbsolutePath();
    }

    private BufferedImage createBuffFromFile(String path) throws IOException {
        File tempImg = new File(path);
        BufferedImage im = ImageIO.read(tempImg);
        tempImg.delete();
        return im;
    }

    private void drawText(TextXml text, Graphics2D projectCanvas){
        projectCanvas.drawString(text.getValue(), text.getX(), text.getY());
    }
}
