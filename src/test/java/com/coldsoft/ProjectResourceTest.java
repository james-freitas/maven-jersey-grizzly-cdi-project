package com.coldsoft;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.coldsoft.projects.ApplicationServer;
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

}
