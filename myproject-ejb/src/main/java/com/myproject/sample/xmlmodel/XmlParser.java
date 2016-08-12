package com.myproject.sample.xmlmodel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParser {
    public ProjectXml parseXml(File xmlDocument) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(ProjectXml.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        ProjectXml project = (ProjectXml) unmarshaller.unmarshal(xmlDocument);
        return project;
    }
}
