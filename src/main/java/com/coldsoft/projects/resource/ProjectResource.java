package com.coldsoft.projects.resource;

import com.coldsoft.projects.model.Project;
import com.coldsoft.projects.service.ProjectService;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("projects")
public class ProjectResource {

  @Inject
  private ProjectService projectService;

  @GET
  @Path("test")
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
      return projectService.toString();
  }


  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_XML)
  public String getProjectById(@PathParam("id") Long id) {
      Project project = projectService.getProjectById(id);
      return project.toXML();
  }

  @POST
  @Consumes(MediaType.APPLICATION_XML)
  public Response addProject(String xmlContent){
      Project projectAdded = projectService.addProjectFromXML(xmlContent);
      URI uri = URI.create("/api/projects/" + projectAdded.getId());
      return Response.created(uri).build();
  }

}
