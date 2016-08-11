package com.myproject.sample.rest;

import com.myproject.sample.model.User;
import com.myproject.sample.service.StorageService;
import com.myproject.sample.service.UserService;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.List;
import java.util.Map;

@Path("/upload")
public class ProjectUploader {
    @Inject private UserService userService;

    @Inject private StorageService storageService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProject(MultipartFormDataInput multipartFormDataInput, @Context SecurityContext context) {
        User uploader = userService.findByUsername(context.getUserPrincipal().getName());

        String uploadFilePath = "";
        try {
            Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
            List<InputPart> lstInputPart = map.get("file");

            for(InputPart inputPart : lstInputPart){
                MultivaluedMap<String, String> multivaluedMap = inputPart.getHeaders();
                String fileName = getFileName(multivaluedMap);

                if(fileName != null && !"".equalsIgnoreCase(fileName)){
                    try(InputStream inputStream = inputPart.getBody(InputStream.class, null)) {
                        uploadFilePath = storageService.saveProject(uploader, inputStream, fileName);
                    }
                }
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            return Response.status(400).entity("Project not saved").build();
        }

        return Response.status(200).entity("Project saved to " + uploadFilePath).build();
    }

    private String getFileName(MultivaluedMap<String, String> multivaluedMap) {

        String[] contentDisposition = multivaluedMap.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "file.unknown";
    }

}
