package com.myproject.sample.xmlmodel;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectXml {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "width")
    private int width;

    @XmlAttribute(name = "height")
    private int height;

    @XmlElement(name = "image")
    private List<ImageXml> images;

    @XmlElement(name = "block")
    private List<BlockXml> blocks;

    @XmlElement(name = "text")
    private List<TextXml> texts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
