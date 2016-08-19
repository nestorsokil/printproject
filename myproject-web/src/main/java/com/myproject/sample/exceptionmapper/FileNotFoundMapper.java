package com.myproject.sample.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.FileNotFoundException;

@Provider
public class FileNotFoundMapper implements ExceptionMapper<FileNotFoundException>{
    @Override public Response toResponse(FileNotFoundException e) {
        e.printStackTrace();
        return Response.status(404).entity("Could not find the requested resource.").build();
    }
}
