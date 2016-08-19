package com.myproject.sample.exceptionmapper;

import com.myproject.sample.exception.UnsuccessfulProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnsuccessfulProcessingMapper implements ExceptionMapper<UnsuccessfulProcessingException>{
    @Override public Response toResponse(UnsuccessfulProcessingException e) {
        e.printStackTrace();
        return Response.status(400).entity(e.getMessage()).build();
    }
}