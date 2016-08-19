package com.myproject.sample.exceptionmapper;

import com.myproject.sample.exception.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedMapper implements ExceptionMapper<AccessDeniedException>{
    @Override public Response toResponse(AccessDeniedException e) {
        e.printStackTrace();
        return Response.status(403).entity(e.getMessage()).build();
    }
}
