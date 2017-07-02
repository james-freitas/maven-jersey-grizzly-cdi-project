package com.coldsoft.projects.resource;

import com.coldsoft.projects.model.Project;
import com.coldsoft.projects.service.ProjectService;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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


}
