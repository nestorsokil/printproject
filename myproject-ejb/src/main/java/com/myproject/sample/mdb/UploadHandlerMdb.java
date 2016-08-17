package com.myproject.sample.mdb;


import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.Project;
import com.myproject.sample.processor.Processor;
import com.myproject.sample.processor.ProjectProcessor;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(name = "UploadHandlerMdb",
        mappedName = "uploadHandleMdb", activationConfig = {
        @ActivationConfigProperty(propertyName="messagingType", propertyValue="javax.jms.MessageListener"),
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="fileUploadQueue"),
        @ActivationConfigProperty(propertyName="connectionFactoryName", propertyValue="myProjectConnectionFactory"),
        @ActivationConfigProperty(propertyName="MaxPoolSize", propertyValue="1"),
        @ActivationConfigProperty(propertyName="MaxMessages", propertyValue="1")
})
public class UploadHandlerMdb implements MessageListener {

    @Inject @Processor("Basic") private ProjectProcessor processor;

    @Override public void onMessage(Message message) {
        try {
            ObjectMessage msg = (ObjectMessage) message;
            processor.process((Project) msg.getObject());
        }catch (JMSException | UnsuccessfulProcessingException je){
            je.printStackTrace();
        }
    }
}
