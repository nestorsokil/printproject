package com.myproject.sample.xmlmodel;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public abstract class AbstractXmlContainer extends AbstractXmlElement {
    @XmlElement(name = "image")
    protected List<ImageXml> images;

    @XmlElement(name = "block")
    protected List<BlockXml> blocks;

    @XmlElement(name = "text")
    protected List<TextXml> texts;

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
