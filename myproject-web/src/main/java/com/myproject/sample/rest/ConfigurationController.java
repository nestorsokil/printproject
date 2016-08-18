package com.myproject.sample.rest;

import com.myproject.sample.config.AppProperty;
import com.myproject.sample.config.ApplicationConfigurator;
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
    public Response downloadConfig(@Context SecurityContext context) throws IOException{
        InputStream stream = new FileInputStream(getPropertiesFile());
        Response.ResponseBuilder builder = Response.ok(stream);
        builder.header("Content-Disposition", "attachment; filename=\"application.properties\"");
        return builder.build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadConfig(@MultipartForm FileUploadForm form,
                                 @Context SecurityContext context) {

        User uploader = userService.findByUsername(context.getUserPrincipal().getName());
        if(!uploader.getRole().equals("ADMIN"))
            return Response.status(403).entity("Access denied").build();

        byte[] data = form.getFileData();
        try (FileOutputStream fos = new FileOutputStream(getPropertiesFile())){
            fos.write(data);
        }catch (IOException ioe){ioe.printStackTrace();}

        config.invalidate();
        return Response.status(200).entity("Configuration saved").build();
    }

    private File getPropertiesFile(){
        return new File(config.getProperty(AppProperty.JBOSS_CONFIG_DIR)
                + File.separator + "application.properties");
    }

}
