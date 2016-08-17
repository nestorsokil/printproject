package com.myproject.sample.xmlmodel;

import com.myproject.sample.util.ProjectFileUtils;
import org.apache.commons.io.FilenameUtils;
import org.faceless.pdf2.PDFImage;
import org.faceless.pdf2.PDFPage;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImageXml extends AbstractXmlElement {
    @XmlAttribute
    protected String imageRef;

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    @Override public void draw(Graphics2D graphics, String projectTempFolderPath) throws IOException {
        File scaledImg = new File(projectTempFolderPath + File.separator + ProjectFileUtils.makeTempImgName(imageRef, width, height));
        BufferedImage bufferedImage = ImageIO.read(scaledImg);
        graphics.drawImage(bufferedImage, x, y, width, height, null);
    }

    @Override public void draw(PDFPage page, String projectTempFolderPath) throws IOException{
        PDFImage im;
        File scaledImg = new File(projectTempFolderPath + File.separator + ProjectFileUtils.makeTempImgName(imageRef, width, height));
        try(InputStream in = new FileInputStream(scaledImg)){
            im = new PDFImage(in);
        }
        page.drawImage(im, x, y, x+width, y+height);
    }
}
