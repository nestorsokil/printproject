package com.myproject.sample.rest;

import com.myproject.sample.exception.AccessDeniedException;
import com.myproject.sample.model.Project;
import com.myproject.sample.model.User;
import com.myproject.sample.service.ProjectService;
import com.myproject.sample.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/delete")
public class ProjectRemover {

    @Inject private UserService userService;

    @Inject private ProjectService projectService;

    //DELETE is better, but GET is easier :)
    @GET
    @Path("/{id}")
    public Response deleteProject(@PathParam("id") String id, @Context SecurityContext context)
            throws AccessDeniedException{
        User user = userService.findByUsername(context.getUserPrincipal().getName());
        Project project = projectService.findById(id);

        if(!user.equals(project.getUser()))
            throw new AccessDeniedException("Only the owner is allowed to delete a project.");
        projectService.delete(project);
        return Response.status(200).entity("Successfully deleted project").build();
    }
}
