package com.myproject.sample.rest;

import com.myproject.sample.dto.ProcessedProjectDto;
import com.myproject.sample.locator.UserStorageResourceLocator;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.User;
import com.myproject.sample.service.ProjectService;
import com.myproject.sample.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.Base64;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Path("/download")
public class ProjectDownloader {
    @Inject private UserService userService;

    @Inject private ProjectService projectService;

    @Inject private UserStorageResourceLocator locator;

    @Path("/count")
    @GET
    public long countProjects(@Context SecurityContext context){
        User user = userService.findByUsername(context.getUserPrincipal().getName());
        if(user.getRole().equals("ADMIN"))
            return projectService.countAll();
        return projectService.countAllByUser(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProcessedProjectDto> getFileList(@Context SecurityContext context,
                                                 @QueryParam("page") int page,
                                                 @QueryParam("size") int size) throws IOException{
        User user = userService.findByUsername(context.getUserPrincipal().getName());
        int startIndex = page * size;
        List<ProcessedProjectDto> result = new ArrayList<>();
        List<Project> projects;
        if(user.getRole().equals("ADMIN"))
            projects = projectService.getResultsPage(startIndex, size);
        else
            projects = projectService.getResultsPageByUser(startIndex, size, user);
        for(Project p : projects){
            ProcessedProjectDto dto = new ProcessedProjectDto(p);
            File img = locator.locate(p, "processed" + File.separator + "thumbnail.png");
            try(InputStream is = new FileInputStream(img)) {
                byte[] bytes = IOUtils.toByteArray(is);
                dto.setThumbnail(Base64.encodeBytes(bytes));
            } catch (IOException ioe){
                ioe.printStackTrace();
            } finally {
                result.add(dto);
            }
        }
        return result;
    }

    @Path("/{id}")
    @GET
    public Response downloadProcessed(@PathParam("id") String id){
        Project project = projectService.findById(id);
        File file = locator.locate(project, "processed" + File.separator + "processed.png");
        if(!file.exists())
            file = locator.locate(project, "processed" + File.separator + "processed.pdf");
        if(!file.exists())
            return Response.status(404).entity("File not found").build();

        Response.ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return responseBuilder.build();
    }
}
