package com.myproject.sample.rest;


import com.myproject.sample.exception.UnsuccessfulProcessingException;
import com.myproject.sample.model.User;
import com.myproject.sample.service.StorageService;
import com.myproject.sample.service.UserService;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;



import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.*;

@Path("/upload")
public class ProjectUploader {
    @Inject private UserService userService;

    @Inject private StorageService storageService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProject(
            @MultipartForm FileUploadForm form,
            @Context SecurityContext context){
        User uploader = userService.findByUsername(context.getUserPrincipal().getName());

        String uploadFilePath;
        try(InputStream is = new ByteArrayInputStream(form.getFileData())) {
            uploadFilePath = storageService.saveProject(uploader, is, form.getName());
        }catch (UnsuccessfulProcessingException | IOException exc){
            exc.getCause().printStackTrace();
            return Response.status(400).entity("project processing failed").build();
        }
        return Response.status(200).entity("Project saved to " + uploadFilePath).build();
    }

}
