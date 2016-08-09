package com.myproject.sample.rest;

import com.myproject.sample.rest.constants.REST_CONST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/download")
public class FileDownloader {
    @Path("/{filename}")
    @GET
    public Response downloadFile(@PathParam("filename") String filename){
        File file = new File(REST_CONST.SAVE_FOLDER + filename);

        Response.ResponseBuilder responseBuilder = Response.ok( file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return responseBuilder.build();
    }
}
