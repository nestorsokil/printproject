package com.myproject.sample.xmlmodel;

import com.myproject.sample.canvas.AbstractCanvas;
import com.myproject.sample.imgprocess.ImageScaler;
import com.myproject.sample.util.ProjectFileUtils;
import org.faceless.pdf2.PDFPage;

import javax.xml.bind.annotation.XmlElement;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractXmlContainer extends AbstractXmlElement {
    @XmlElement(name = "image")
    protected List<ImageXml> images = new LinkedList<>();

    @XmlElement(name = "block")
    protected List<BlockXml> blocks = new LinkedList<>();

    @XmlElement(name = "text")
    protected List<TextXml> texts = new LinkedList<>();

    public List<ImageXml> getImages() {
        return images;
    }

    public void setImages(List<ImageXml> images) {
        this.images = images;
    }

    public List<BlockXml> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockXml> blocks) {
        this.blocks = blocks;
    }

    public List<TextXml> getTexts() {
        return texts;
    }

    public void setTexts(List<TextXml> texts) {
        this.texts = texts;
    }

    public void fixAllChildCoordinates(){
        for(ImageXml el: images){
            el.setElementAbsoluteCoordinates(this);
        }

        for(TextXml el: texts){
            el.setElementAbsoluteCoordinates(this);
        }

        for(BlockXml el: blocks){
            el.setElementAbsoluteCoordinates(this);
            el.fixAllChildCoordinates();
        }
    }

    public void scaleAllImages(ImageScaler scaler, String projectFolderPath, String projectTempFolderPath) throws IOException{
        for(ImageXml im: images){
            int width = im.getWidth(), height = im.getHeight();
            String imgRef = im.getImageRef();

            File sourceImg = new File(projectFolderPath + File.separator + imgRef);
            File targetImg = new File(projectTempFolderPath + File.separator + ProjectFileUtils.makeTempImgName(imgRef, width, height));
            if(!targetImg.exists())
                scaler.scale(sourceImg, targetImg, width, height);
        }

        for(BlockXml bl: blocks){
            bl.scaleAllImages(scaler, projectFolderPath, projectTempFolderPath);
        }
    }

    public void draw(PDFPage page, String projectTempFolderPath) throws IOException {
        List<AbstractXmlElement> allElements = new ArrayList<>();
        allElements.addAll(images);
        allElements.addAll(texts);
        allElements.addAll(blocks);

        for(AbstractXmlElement el: allElements){
            el.draw(page, projectTempFolderPath);
        }
    }

    @Override public void draw(Graphics2D graphics, String projectTempFolderPath) throws IOException {
        List<AbstractXmlElement> allElements = new ArrayList<>();
        allElements.addAll(images);
        allElements.addAll(texts);
        allElements.addAll(blocks);

        for(AbstractXmlElement el: allElements){
            el.draw(graphics, projectTempFolderPath);
        }
    }
}
