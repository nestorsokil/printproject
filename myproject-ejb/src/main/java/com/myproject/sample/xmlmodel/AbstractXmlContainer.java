package com.myproject.sample.xmlmodel;

import com.myproject.sample.canvas.ICanvas;
import com.myproject.sample.exception.UnsuccessfulProcessingException;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
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

    public void drawOnCanvas(ICanvas canvas) throws UnsuccessfulProcessingException {
        List<AbstractXmlElement> allElements = new ArrayList<>();
        allElements.addAll(images);
        allElements.addAll(texts);
        allElements.addAll(blocks);

        for (AbstractXmlElement el: allElements){
            el.setElementAbsoluteCoordinates(this);
            el.drawOnCanvas(canvas);
        }
    }
}
