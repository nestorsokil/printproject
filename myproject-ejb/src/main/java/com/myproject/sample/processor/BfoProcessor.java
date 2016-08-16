package com.myproject.sample.processor;

import com.myproject.sample.Processor;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.xmlmodel.*;
import org.faceless.pdf2.*;


import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.*;

//TODO: REMOVE CODE DUPLICATION

@Processor("PDF")
public class BfoProcessor implements ProjectProcessor {
    @Inject private UserStorageResourceLocator userLocator;

    @Inject private TempStorageResourceLocator tempLocator;

    @Inject @FactoryProducedScaler private ImageScaler imageScaler;

    @Inject private XmlParser parser;

    @Override public void process(Project project) throws UnsuccessfulProcessingException {

        File xmlScheme = userLocator.locate(project, "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            throw new UnsuccessfulProcessingException(je);
        }

        PDF pdf = new PDF();
        PDFPage pdfCanvas = pdf.newPage(projectXml.getWidth(), projectXml.getHeight());

        try {
            processXmlContainer(projectXml, pdfCanvas, project);
        }catch (IOException ioe){throw new UnsuccessfulProcessingException(ioe);}

        File processedFolder = userLocator.locate(project, "processed");
        processedFolder.mkdir();
        File processed = new File(processedFolder.getAbsolutePath() + File.separator + "processed.pdf");

        try(OutputStream out = new FileOutputStream(processed)){
            pdf.render(out);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }


    private void processXmlContainer(AbstractXmlContainer containerXml, PDFPage canvas, Project project)
            throws IOException{
        for(ImageXml im : containerXml.getImages()){
            setElementAbsoluteCoordinates(containerXml, im);
            drawImage(im, canvas, project);
        }

        for(TextXml tx : containerXml.getTexts()){
            setElementAbsoluteCoordinates(containerXml, tx);
            drawText(tx, canvas);
        }

        for(BlockXml bl : containerXml.getBlocks()){
            setElementAbsoluteCoordinates(containerXml, bl);
            processXmlContainer(bl, canvas, project);
        }
    }

    private void setElementAbsoluteCoordinates(AbstractXmlContainer parent, AbstractXmlElement child){
        child.setX(parent.getX() + child.getX());
        child.setY(parent.getY() + child.getY());
    }

    private void drawImage(ImageXml image, PDFPage projectCanvas, Project project) throws IOException {
        String pathToTempImg = scaleImage(image, project);
        PDFImage im = createPdfImage(pathToTempImg);
        projectCanvas.drawImage(im, image.getX(),
                image.getY(), image.getX()+image.getWidth(), image.getY()+image.getHeight());
    }

    private String scaleImage(ImageXml imageXml, Project project) throws IOException{
        int width = imageXml.getWidth(), height = imageXml.getHeight();
        String imgRef = imageXml.getImageRef();

        File sourceImg = userLocator.locate(project, imgRef);
        File targetImg = tempLocator.locate(ProjectFileUtils.makeTempImgName(imgRef, width, height));
        imageScaler.scale(sourceImg, targetImg, width, height);

        return targetImg.getAbsolutePath();
    }

    private PDFImage createPdfImage(String path) throws IOException{
        PDFImage im;
        try(InputStream in = new FileInputStream(path)){
            im = new PDFImage(in);
            new File(path).delete();
        }
        return im;
    }

    private void drawText(TextXml textXml, PDFPage canvas){
        PDFStyle normal = new PDFStyle();
        normal.setFont(new StandardFont(StandardFont.TIMES), textXml.getFontSize());
        normal.setFillColor(Color.black);
        normal.setTextLineSpacing(1.5f);
        canvas.setStyle(normal);
        canvas.drawText(textXml.getValue(), textXml.getX(), textXml.getY());
    }
}
