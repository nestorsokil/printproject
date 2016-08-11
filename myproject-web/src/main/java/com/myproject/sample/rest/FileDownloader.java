package com.myproject.sample.rest;

import com.myproject.sample.model.Project;
import com.myproject.sample.model.User;
import com.myproject.sample.service.ProjectService;
import com.myproject.sample.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("/download")
public class FileDownloader {
    @Inject private UserService userService;

    @Inject private ProjectService projectService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFileList(@Context SecurityContext context){
        User user = userService.findByUsername(context.getUserPrincipal().getName());
        List<String> result = new ArrayList<>();
        List<Project> projects;
        if(user.getRole().equals("ADMIN"))
            projects = projectService.findAll();
        else
            projects = user.getProjects();
        for(Project p : projects){
            result.add(p.getName());
        }
        return result;
    }

    //TODO: update method
    @Path("/{filename}")
    @GET
    public Response downloadFile(@PathParam("filename") String filename){
        File file = new File(System.getenv("JBOSS_HOME") +
                "\\standalone\\my_uploads\\" + filename);

        Response.ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        return responseBuilder.build();
    }
}
