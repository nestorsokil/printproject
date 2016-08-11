package com.myproject.sample.rest;

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

    @GET
    @Path("/dload")
    public Response downloadConfig(@Context SecurityContext context) throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = new FileInputStream("C:/app-properties/application.properties");

        Response.ResponseBuilder builder = Response.ok(stream);
        builder.header("Content-Disposition", "attachment; filename=\"application.properties\"");
        return builder.build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadConfig(@MultipartForm FileUploadForm form, @Context SecurityContext context) {
        User uploader = userService.findByUsername(context.getUserPrincipal().getName());
        if(!uploader.getRole().equals("ADMIN"))
            return Response.status(403).entity("Access denied").build();

        byte[] data = form.getFileData();
        if(data == null){return Response.status(400).entity("data is null").build();}
        File file = new File("C:/app-properties/application.properties");
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(data);
        }catch (IOException ioe){ioe.printStackTrace();}

        return Response.status(200).entity("Configuration saved").build();
    }

}
