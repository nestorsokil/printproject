package com.myproject.sample.canvas;

import com.myproject.sample.xmlmodel.ImageXml;
import com.myproject.sample.xmlmodel.TextXml;
import org.faceless.pdf2.*;

import javax.inject.Named;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Named("PDF")
public class PdfCanvas implements AbstractCanvas {
    private PDFPage pdfPage;

    public PdfCanvas(PDFPage pdfPage) {
        this.pdfPage = pdfPage;
        pdfPage.setUnits(PDFPage.UNITS_POINTS, PDFPage.ORIGIN_PAGETOP);
    }

    @Override public void drawImage(ImageXml image, File tempImg) throws IOException {
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

    private PDFImage createPdfImage(File path) throws IOException {
        try(InputStream in = new FileInputStream(path)){
            return new PDFImage(in);
        }
    }
}
