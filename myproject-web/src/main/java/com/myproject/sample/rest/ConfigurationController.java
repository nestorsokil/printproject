package com.myproject.sample.rest;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;
import com.myproject.sample.exception.AccessDeniedException;
import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.User;
import com.myproject.sample.service.UserService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.*;

@Path("/config")
public class ConfigurationController {
    @Inject private UserService userService;

    @Inject private ApplicationConfigurator config;

    @GET
    @Path("/dload")
    public Response downloadConfig(@Context SecurityContext context) throws AccessDeniedException, FileNotFoundException{
        User downloader =  userService.findByUsername(context.getUserPrincipal().getName());
        if(!downloader.getRole().equals("ADMIN"))
            throw new AccessDeniedException("Only administrator is allowed to access configuration.");
        InputStream stream = new FileInputStream(getPropertiesFile());
        Response.ResponseBuilder builder = Response.ok(stream);
        builder.header("Content-Disposition", "attachment; filename=\"application.properties\"");
        return builder.build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadConfig(@MultipartForm FileUploadForm form, @Context SecurityContext context)
            throws AccessDeniedException, FileNotFoundException, UnsuccessfulProcessingException{
        User uploader = userService.findByUsername(context.getUserPrincipal().getName());
        if(!uploader.getRole().equals("ADMIN"))
            throw new AccessDeniedException("Only administrator is allowed to modify configuration.");
        byte[] data = form.getFileData();
        try (FileOutputStream fos = new FileOutputStream(getPropertiesFile())){
            fos.write(data);
        } catch (IOException ioe){
            ioe.printStackTrace();
            throw new UnsuccessfulProcessingException("Could not read uploaded data.");
        }
        config.invalidate();
        return Response.status(200).entity("Configuration saved").build();
    }

    private File getPropertiesFile(){
        return new File(config.getProperty(AppProperty.JBOSS_CONFIG_DIR)
                + File.separator + "application.properties");
    }

}
