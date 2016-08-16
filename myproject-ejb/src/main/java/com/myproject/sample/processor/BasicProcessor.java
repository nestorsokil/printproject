package com.myproject.sample.processor;

import com.myproject.sample.canvas.AbstractCanvas;
import com.myproject.sample.canvas.PdfCanvas;
import com.myproject.sample.canvas.PngCanvas;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.imgprocess.FactoryProducedScaler;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.locator.TempStorageResourceLocator;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.xmlmodel.*;
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

@Processor("Basic")
public class BasicProcessor implements ProjectProcessor {

    @Inject private XmlParser parser;

    @Inject private TempStorageResourceLocator tempLocator;

    @Inject private UserStorageResourceLocator userLocator;

    @Inject @FactoryProducedScaler private ImageScaler imageScaler;

    private Project processingProject;

    @Override public void process(Project project) throws UnsuccessfulProcessingException {
        this.processingProject = project;
        File xmlScheme = userLocator.locate(project, "project.xml");
        ProjectXml projectXml;
        try {
            projectXml = parser.parseXml(xmlScheme);
        }catch (JAXBException je){
            throw new UnsuccessfulProcessingException(je);
        }

        projectXml.fixAllChildCoordinates();
        File processedFolder = userLocator.locate(project, "processed");
        processedFolder.mkdirs();

        try {
            BufferedImage im = processPng(projectXml);
            File processedPng = new File(processedFolder, "processed.png");
            ImageIO.write(im, "png", processedPng);

            PDF pdf = processPdf(projectXml);
            File processedPdf = new File(processedFolder, "processed.pdf");
            OutputStream out = new FileOutputStream(processedPdf);
            pdf.render(out);
            out.close();

        }catch (IOException ioe){
            throw new UnsuccessfulProcessingException(ioe);
        }
    }

    private BufferedImage processPng(ProjectXml projectXml) throws IOException{
        BufferedImage image = new BufferedImage(projectXml.getWidth(), projectXml.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        AbstractCanvas canvas = new PngCanvas(graphics2D);
        processXmlContainer(projectXml, canvas);
        return image;

    }

    private PDF processPdf(ProjectXml projectXml) throws IOException{
        PDF pdf = new PDF();
        PDFPage pdfPage = pdf.newPage(projectXml.getWidth(), projectXml.getHeight());
        AbstractCanvas canvas = new PdfCanvas(pdfPage);
        processXmlContainer(projectXml, canvas);
        return pdf;
    }

    public void processXmlContainer(AbstractXmlContainer container, AbstractCanvas canvas)
            throws IOException {
        for(ImageXml im : container.getImages()){
            File scaledImage = scaleImage(im);
            canvas.drawImage(im, scaledImage);
        }

        for(TextXml tx : container.getTexts()){
            canvas.drawText(tx);
        }

        for(BlockXml bl : container.getBlocks()){
            processXmlContainer(bl, canvas);
        }
    }

    protected File scaleImage(ImageXml imageXml) throws IOException{
        int width = imageXml.getWidth(), height = imageXml.getHeight();
        String imgRef = imageXml.getImageRef();

        File sourceImg = userLocator.locate(processingProject, imgRef);
        File targetImg = tempLocator.locate(ProjectFileUtils.makeTempImgName(imgRef, width, height));
        imageScaler.scale(sourceImg, targetImg, width, height);

        return targetImg;
    }
}
