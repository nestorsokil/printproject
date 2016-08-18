package com.myproject.sample.validator;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlValidator {

    @Inject private ApplicationConfigurator appConfig;

    public void validate(InputStream xml) throws IOException, SAXException{
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File xsd = new File(appConfig.getProperty(AppProperty.JBOSS_CONFIG_DIR)
                + File.separator + "validation.schema.xsd");

        Schema schema = factory.newSchema(xsd);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
    }
}
