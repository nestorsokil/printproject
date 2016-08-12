package com.myproject.sample.processor;

import com.myproject.sample.config.ApplicationConfigurator;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.model.Project;
import com.myproject.sample.xmlmodel.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Graphics2DProcessor implements ProjectProcessor{
    @Inject private XmlParser parser;

    @Inject
    @Named(ApplicationConfigurator.IMAGICK_SCALER_BEAN_NAME)
    private ImageScaler scaler;

    @Inject private ApplicationConfigurator appConfig;

    private String beanQualifier;

    @Override public void process(Project project) throws UnsuccessfulProcessingException{
        String projectPath = appConfig.getUserStoragePath() + File.separator + project.getId();
        File xmlScheme = new File(projectPath + File.separator + "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            throw new UnsuccessfulProcessingException(je);
        }

        BufferedImage image = new BufferedImage(projectXml.getWidth(), projectXml.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D canvas = image.createGraphics();
        try {
            processXmlContainer(projectXml, canvas, projectPath);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }

        String pathToResult = projectPath + File.separator + project.getName() + "-processed.png";
        File processedFile = new File(pathToResult);
        try {
            ImageIO.write(image, "png", processedFile);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }

    private void processXmlContainer(AbstractXmlContainer containerXml, Graphics2D canvas, String pathToProject)
            throws IOException{
        List<ImageXml> images = containerXml.getImages();
        for(ImageXml im : images){
            setElementAbsoluteCoordinates(containerXml, im);
            drawImage(im, canvas, pathToProject);
        }

        List<TextXml> texts = containerXml.getTexts();
        for(TextXml tx : texts){
            setElementAbsoluteCoordinates(containerXml, tx);
            drawText(tx, canvas);
        }

        List<BlockXml> blocks = containerXml.getBlocks();
        for(BlockXml bl : blocks){
            setElementAbsoluteCoordinates(containerXml, bl);
            processXmlContainer(bl, canvas, pathToProject);
        }

    }

    private void setElementAbsoluteCoordinates(AbstractXmlElement parent, AbstractXmlElement child){
        child.setX(parent.getX() + child.getX());
        child.setY(parent.getY() + child.getY());
    }

    private void drawImage(ImageXml image, Graphics2D projectCanvas, String projectFolder)throws IOException{
        String pathToTempImg = scaleImage(image, projectFolder);
        BufferedImage imgBuff = createBuffFromFile(pathToTempImg);
        projectCanvas.drawImage(imgBuff, image.getX(), image.getY(), null);
    }

    private String scaleImage(ImageXml imageXml, String projectFolder){
        int width = imageXml.getWidth(), height = imageXml.getHeight();
        String sep = File.separator;

        String imgRef = imageXml.getImageRef();
        String sourceImgName = projectFolder + sep + imageXml.getImageRef();


        String targetImgName = appConfig.getTempStoragePath() + sep + imageXml.getImageRef() + width + "x" + height;
        scaler.scale(sourceImgName, targetImgName, width, height);

        return targetImgName;
    }

    private BufferedImage createBuffFromFile(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    private void drawText(TextXml text, Graphics2D projectCanvas){
        projectCanvas.drawString(text.getValue(), text.getX(), text.getY());
    }
}
