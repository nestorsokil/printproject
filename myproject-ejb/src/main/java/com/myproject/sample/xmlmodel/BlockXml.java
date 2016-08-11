package com.myproject.sample.xmlmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class BlockXml {
    @XmlAttribute
    private int x;

    @XmlAttribute
    private  int y;

    @XmlAttribute
    private int width;

    @XmlAttribute
    private int height;

    @XmlElement(name = "block")
    private List<BlockXml> block;

    @XmlElement(name = "image")
    private List<ImageXml> images;

    @XmlAttribute(name = "text")
    private List<TextXml> texts;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<BlockXml> getBlock() {
        return block;
    }

    public void setBlock(List<BlockXml> block) {
        this.block = block;
    }

    public List<ImageXml> getImages() {
        return images;
    }

    public void setImages(List<ImageXml> images) {
        this.images = images;
    }

    public List<TextXml> getTexts() {
        return texts;
    }

    public void setTexts(List<TextXml> texts) {
        this.texts = texts;
    }
}
