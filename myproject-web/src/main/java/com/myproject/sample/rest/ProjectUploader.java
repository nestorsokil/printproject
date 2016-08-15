package com.myproject.sample.rest;

import com.myproject.sample.exception.InvalidUploadException;
import com.myproject.sample.model.User;
import com.myproject.sample.service.StorageService;
import com.myproject.sample.service.UserService;
import com.myproject.sample.util.UploadUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

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
    public Response uploadProject(MultipartFormDataInput multipartFormDataInput, @Context SecurityContext context) {
        User uploader = userService.findByUsername(context.getUserPrincipal().getName());

        String uploadFilePath;
        try {
            InputPart fileInputPart = UploadUtils.getData(multipartFormDataInput);
            String filename = UploadUtils.getFileName(fileInputPart.getHeaders());

            uploadFilePath = storageService.saveProject(uploader,
                    fileInputPart.getBody(InputStream.class, null), filename);
        }catch (IOException | InvalidUploadException ioe){
            ioe.printStackTrace();
            return Response.status(400).entity("Project not saved").build();
        }

        return Response.status(200).entity("Project saved to " + uploadFilePath).build();
    }

}
