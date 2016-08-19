package com.myproject.sample.service;

import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.Storage;
import com.myproject.sample.model.User;
import com.myproject.sample.processor.Processor;
import com.myproject.sample.processor.ProjectProcessor;
import com.myproject.sample.util.ProjectFileUtils;
import com.myproject.sample.validator.XmlValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import java.io.*;
import java.util.Calendar;
import java.util.List;

public class ProjectServiceImpl extends GenericServiceImpl<Project> implements ProjectService {

    @Inject private StorageService storageService;

    @Inject @Processor("Basic") private ProjectProcessor processor;

    @Inject private UserStorageResourceLocator userStorageResourceLocator;

    @Inject private XmlValidator xmlValidator;

    @Override public void delete(Project entity) {
        File projectFolder = userStorageResourceLocator.locate(entity, UserStorageResourceLocator.FOLDER_ROOT);
        try {
            FileUtils.deleteDirectory(projectFolder);
        }catch (IOException ioe){ioe.printStackTrace();}
        super.delete(entity);
    }

    @Override
    public String saveProject(User uploader, InputStream fileStream, String fileName)
            throws UnsuccessfulProcessingException {
        Storage userStorage = storageService.getStorageById("storage_user");
        Project project = new Project();
        project.setName(FilenameUtils.getBaseName(fileName) + "("
                + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss") + ")");
        project.setUser(uploader);
        project.setStorage(userStorage);
        project = this.update(project);

        String projectPath = userStorage.getPath() + File.separator + project.getId();
        File projectFolder = new File(projectPath);

        if(!projectFolder.mkdir())
            throw new UnsuccessfulProcessingException("Could not create folder");

        try {
            ProjectFileUtils.unzipProject(fileStream, projectPath);
        }catch (IOException ioe){
            ioe.printStackTrace();
            projectFolder.delete();
            dao.delete(project);
            throw new UnsuccessfulProcessingException("Unzipping failed");
        }

        try(InputStream is = new FileInputStream(userStorageResourceLocator.locate(project, "project.xml"))){
            xmlValidator.validate(is);
        }catch (SAXException exc){
            exc.printStackTrace();
            throw new UnsuccessfulProcessingException("validation failed");
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
            throw new UnsuccessfulProcessingException("project.xml not found");
        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new UnsuccessfulProcessingException("schema not found?");
        }

        processor.process(project);
        //OR
        //invokeMdbProcessor(project);
        return projectPath;
    }

    //async call to MDB
    //just for training, because of using REST calls processing is already asynchronous

    @Resource(name = "myProjectConnectionFactory") private ConnectionFactory connectionFactory;
    @Resource(name = "fileUploadQueue") private Destination destination;

    private void invokeMdbProcessor(Project project){
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage(project);
            producer.send(message);
            session.close();
            connection.close();
        }catch (JMSException je){je.printStackTrace();}
    }


    //for pagination

    @Override
    public long countAll(){
        return (long) dao.getEntityManager().createQuery("Select count(*) from Project").getSingleResult();
    }

    @Override
    public long countAllByUser(User user){
        return (long) dao.getEntityManager().createQuery("Select count(*) from Project p where p.user = :u")
                .setParameter("u", user)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> getResultsPage(int startIndex, int pageSize){
        return dao.getEntityManager()
                .createQuery("from Project")
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> getResultsPageByUser(int startIndex, int pageSize, User user){
        return dao.getEntityManager()
                .createQuery("Select p from Project p where user = :u")
                .setParameter("u", user)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
