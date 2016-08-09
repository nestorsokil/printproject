package com.myproject.sample.rest;

import com.myproject.sample.rest.constants.REST_CONST;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;

@Path("/upload")
public class FileUploader {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput multipartFormDataInput) {
        String uploadFilePath = "";
        try {
            Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
            List<InputPart> lstInputPart = map.get("file");

            for(InputPart inputPart : lstInputPart){
                MultivaluedMap<String, String> multivaluedMap = inputPart.getHeaders();
                String fileName = getFileName(multivaluedMap);

                if(null != fileName && !"".equalsIgnoreCase(fileName)){
                    try( InputStream inputStream = inputPart.getBody(InputStream.class, null)) {
                        uploadFilePath = writeToFileServer(inputStream, fileName);
                    }
                }
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            return Response.status(400).entity("File not saved").build();
        }

        return Response.status(200).entity("File saved to " + uploadFilePath).build();
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

    private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {

        String qualifiedUploadFilePath = REST_CONST.SAVE_FOLDER + fileName;

        try (OutputStream outputStream = new FileOutputStream(new File(qualifiedUploadFilePath))){
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return qualifiedUploadFilePath;
    }
}
