package com.myproject.sample.processor;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.xmlmodel.ProjectXml;
import com.myproject.sample.xmlmodel.XmlParser;
import org.apache.commons.io.FileUtils;
import org.faceless.pdf2.PDF;
import org.faceless.pdf2.PDFPage;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Processor("Objective")
public class ObjectModelProcessor implements ProjectProcessor {
    @Inject private XmlParser parser;

    @Inject private TempStorageResourceLocator tempLocator;

    @Inject private UserStorageResourceLocator userLocator;

    @Inject @FactoryProducedScaler private ImageScaler imageScaler;

    @Override public void process(Project project) throws UnsuccessfulProcessingException {
        File xmlScheme = userLocator.locate(project, "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            throw new UnsuccessfulProcessingException(je);
        }

        projectXml.fixAllChildCoordinates();

        String pathToProject = userLocator.locate(project, "").getAbsolutePath();
        File tempProjectFolder = tempLocator.locate(project.getId());
        tempProjectFolder.mkdir();

        String pathToTempFolder = tempProjectFolder.getAbsolutePath();
        try {
            projectXml.scaleAllImages(imageScaler, pathToProject, pathToTempFolder);

            File processedFolder = userLocator.locate(project, "processed");
            processedFolder.mkdirs();

            PDF pdf = new PDF();
            PDFPage pdfPage = pdf.newPage(projectXml.getWidth(), projectXml.getHeight());
            projectXml.draw(pdfPage, pathToTempFolder);
            File processedPdf = new File(processedFolder, "processed.pdf");
            OutputStream out = new FileOutputStream(processedPdf);
            pdf.render(out);
            out.close();

            BufferedImage image = new BufferedImage(projectXml.getWidth(), projectXml.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            projectXml.draw(graphics2D, pathToTempFolder);
            File processedPng = new File(processedFolder, "processed.png");
            ImageIO.write(image, "png", processedPng);
            FileUtils.deleteDirectory(tempProjectFolder);
        } catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }
}
