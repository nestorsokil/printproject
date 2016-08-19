package com.myproject.sample.xmlmodel;

import com.myproject.sample.canvas.ICanvas;
import com.myproject.sample.exception.UnsuccessfulProcessingException;

import javax.xml.bind.annotation.*;
import java.io.File;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectXml extends AbstractXmlContainer{
    @XmlAttribute(name = "name")
    private String name;

    @XmlTransient
    protected int x = 0;

    @XmlTransient
    protected int y = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public void drawOnCanvas(ICanvas canvas, File projectFolder) throws UnsuccessfulProcessingException {
        canvas.init(this, projectFolder);
        super.drawOnCanvas(canvas, projectFolder);
    }
}
