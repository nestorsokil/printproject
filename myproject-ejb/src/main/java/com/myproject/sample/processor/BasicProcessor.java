package com.myproject.sample.processor;

import com.myproject.sample.canvas.FactoryProducedCanvas;
import com.myproject.sample.canvas.ICanvas;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.xmlmodel.*;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Project processor that uses canvases to create images
 */

@Processor("Basic")
public class BasicProcessor implements ProjectProcessor {

    @Inject private XmlParser parser;

    @Inject private UserStorageResourceLocator userLocator;

    @Inject @FactoryProducedCanvas private ICanvas canvas;

    @Override public void process(Project project) throws UnsuccessfulProcessingException {
        File xmlScheme = userLocator.locate(project, "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            je.printStackTrace();
            throw new UnsuccessfulProcessingException("Unable to parse XML");
        }

        File projectFolder = userLocator.locate(project, UserStorageResourceLocator.FOLDER_ROOT);
        File processedFolder = userLocator.locate(project, "processed");
        processedFolder.mkdirs();

        projectXml.drawOnCanvas(canvas, projectFolder);
        canvas.generate(processedFolder);
    }

}
