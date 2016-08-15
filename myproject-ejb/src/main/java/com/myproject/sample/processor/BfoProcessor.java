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
import org.faceless.pdf2.PDFImage;
import org.faceless.pdf2.PDFPage;
import org.faceless.pdf2.PDFStyle;
import org.faceless.pdf2.StandardFont;


import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.*;

//TODO: fix processed file creation
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

        PDFPage pdfCanvas = new PDFPage(projectXml.getWidth(), projectXml.getHeight());

        try {
            processXmlContainer(projectXml, pdfCanvas, project);
        }catch (IOException ioe){throw new UnsuccessfulProcessingException(ioe);}

        File processed = userLocator.locate(project, "processed" + File.separator + "processed.pdf");
        processed.mkdir();
        try(OutputStream out = new FileOutputStream(processed)){
            pdfCanvas.getPDF().render(out);
        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }


    private void processXmlContainer(AbstractXmlContainer containerXml, PDFPage canvas, Project project)
            throws IOException{
        java.util.List<ImageXml> images = containerXml.getImages();
        for(ImageXml im : images){
            setElementAbsoluteCoordinates(containerXml, im);
            drawImage(im, canvas, project);
        }

        java.util.List<TextXml> texts = containerXml.getTexts();
        for(TextXml tx : texts){
            setElementAbsoluteCoordinates(containerXml, tx);
            drawText(tx, canvas);
        }

        java.util.List<BlockXml> blocks = containerXml.getBlocks();
        for(BlockXml bl : blocks){
            setElementAbsoluteCoordinates(containerXml, bl);
            processXmlContainer(bl, canvas, project);
        }
    }

    private void setElementAbsoluteCoordinates(AbstractXmlElement parent, AbstractXmlElement child){
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
        normal.setFont(new StandardFont(StandardFont.TIMES), 12);
        normal.setFillColor(Color.black);
        normal.setTextLineSpacing(1.5f);
        canvas.setStyle(normal);
        canvas.drawText(textXml.getValue(), textXml.getX(), textXml.getY());
    }
}
