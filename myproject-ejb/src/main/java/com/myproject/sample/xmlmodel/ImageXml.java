package com.myproject.sample.xmlmodel;

import com.myproject.sample.canvas.ICanvas;
import com.myproject.sample.exception.UnsuccessfulProcessingException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

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

    @Override public void drawOnCanvas(ICanvas canvas) throws UnsuccessfulProcessingException {
        canvas.drawImage(this);
    }
}
