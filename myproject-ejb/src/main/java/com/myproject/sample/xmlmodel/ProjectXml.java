package com.myproject.sample.xmlmodel;

import javax.xml.bind.annotation.*;

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
}
