package com.myproject.sample.rest;

import com.myproject.sample.rest.constants.REST_CONST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("/download")
public class FileDownloader {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFileList(){
        List<String> result = new ArrayList<>();
        File[] files = new File(REST_CONST.SAVE_FOLDER).listFiles();
        if(files!=null)
            for (File file : files)
                if (file.isFile())
                    result.add(file.getName());
        return result;
    }

    @Path("/{filename}")
    @GET
    public Response downloadFile(@PathParam("filename") String filename){
        File file = new File(REST_CONST.SAVE_FOLDER + filename);

        Response.ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return responseBuilder.build();
    }
}
