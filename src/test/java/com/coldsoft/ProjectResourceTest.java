package com.coldsoft;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coldsoft.projects.ApplicationServer;
import com.coldsoft.projects.dao.ProjectDao;
import com.coldsoft.projects.model.Project;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectResourceTest {

    private HttpServer server;
    private WebTarget target;
    private Client client;


    @Before
    public void setUp() throws Exception {
        this.server = ApplicationServer.startServer();
        this.client = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and ApplicationServer.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        this.target = client.target(ApplicationServer.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testIfResourceIsAccessibleUsingInjectedService() {
        String responseMsg = target.path("projects/test").request().get(String.class);
        assertEquals("ProjectServiceInstance", responseMsg);
    }

    @Test
    public void testShouldGetAProjectByItsId() {
        String response = target.path("projects/1").request().get(String.class);
        assertTrue(response.contains("Awesome Project"));
    }

    @Test
    public void testShouldInsertAProjectAndGetItByItsBrandNewResource(){
        Project project = new Project("Basic Project", 2002);
        String xml = project.toXML();
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("projects").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String content = client.target(location).request().get(String.class);
        Assert.assertTrue(content.contains("Basic Project"));
    }

    @Test
    public void testShouldDeleteAProjectAndReturnOkResponse(){
        Project projectToDelete = new Project("Project to delete", 2011);
        Project projectAdded = new ProjectDao().add(projectToDelete);
        Assert.assertEquals(2l, projectAdded.getId());

        target.path("projects/2").request().delete(String.class);
        Project projectDeleted = new ProjectDao().getProjectById(2l);
        Assert.assertNull(projectDeleted);
    }

    @Test
    public void testShouldEditAProjectAndReturnOkResponse(){
        ProjectDao projectDao = new ProjectDao();
        Project project = projectDao.getProjectById(1l);
        Assert.assertEquals("Awesome Project", project.getName());

        project.setName("Awesome Project - Modified");
        String xml = project.toXML();
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("projects/1").request().put(entity);
        Assert.assertEquals(200, response.getStatus());

        String newResponse = target.path("projects/1").request().get(String.class);
        Assert.assertTrue(newResponse.contains("Awesome Project - Modified"));

        setDatabaseToOriginalState(project);
    }

    private void setDatabaseToOriginalState(Project project) {
        project.setName("Awesome Project");
        project.setYear(2017);
        String xmlBackToOldState = project.toXML();
        Entity<String> entityBackToOldState = Entity.entity(xmlBackToOldState, MediaType.APPLICATION_XML);
        Response responseBackToOldState = target.path("projects/1").request().put(entityBackToOldState);
        Assert.assertEquals(200, responseBackToOldState.getStatus());
    }

    @Test
    public void testShouldModifyOnlyAProjectYearAndReturnOkResponse(){
        Project project = new ProjectDao().getProjectById(1L);
        Assert.assertEquals("Awesome Project", project.getName());
        Assert.assertEquals(2017, project.getYear());

        project.setYear(2099);
        String xml = project.toXML();
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("projects/1/year").request().put(entity);
        Assert.assertEquals(200, response.getStatus());

        String checkResponse = target.path("projects/1").request().get(String.class);
        Assert.assertTrue(checkResponse.contains("2099"));

        setDatabaseToOriginalState(project);
    }

}
