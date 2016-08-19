package com.myproject.sample.canvas;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.ProjectXml;
import com.myproject.sample.xmlmodel.TextXml;
import org.faceless.pdf2.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class PdfCanvas extends AbstractCanvas implements ICanvas{

    private PDF pdf;
    private PDFPage pdfPage;

    @Override public void init(ProjectXml projectXml, File projectFolder) {
        pdf = new PDF();
        pdfPage = pdf.newPage(projectXml.getWidth(), projectXml.getHeight());
        pdfPage.setUnits(PDFPage.UNITS_POINTS, PDFPage.ORIGIN_PAGETOP);
        this.projectFolder = projectFolder;
        super.init(projectXml, projectFolder);
    }

    @Override public void drawImage(ImageXml image) throws UnsuccessfulProcessingException{
        File tempImg = scale(image);
        PDFImage im = createPdfImage(tempImg);
        tempImg.delete();
        pdfPage.drawImage(im, image.getX(),
                image.getY(), image.getX()+image.getWidth(), image.getY()+image.getHeight());
    }

    @Override public void drawText(TextXml textXml) {
        PDFStyle normal = new PDFStyle();
        normal.setFont(new StandardFont(StandardFont.TIMES), textXml.getFontSize());
        normal.setFillColor(Color.black);
        normal.setTextLineSpacing(1.5f);
        pdfPage.setStyle(normal);
        pdfPage.drawText(textXml.getValue(), textXml.getX(), textXml.getY());
    }

    private PDFImage createPdfImage(File path) throws UnsuccessfulProcessingException {
        try(InputStream in = new FileInputStream(path)){
            return new PDFImage(in);
        }catch (IOException ioe){throw new UnsuccessfulProcessingException("Could not find scaled image");}
    }

    @Override public void generate(File folder) throws UnsuccessfulProcessingException {
        File processedPdf = new File(folder, "processed.pdf");
        File thumbnailPng = new File(folder, "thumbnail.png");

        try(OutputStream out = new FileOutputStream(processedPdf)) {
            pdf.render(out);
            PDFParser parser = new PDFParser(pdf);
            PagePainter pagePainter = parser.getPagePainter(0);
            BufferedImage image = pagePainter.getImage(20, PDFParser.RGB);
            ImageIO.write(image, "png", thumbnailPng);
            imageScaler.scale(thumbnailPng, thumbnailPng, 200, 200);
        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new UnsuccessfulProcessingException("Could not save result");
        }
    }
}
