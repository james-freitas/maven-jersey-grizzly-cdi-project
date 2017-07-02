package com.coldsoft;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.coldsoft.projects.ApplicationServer;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = ApplicationServer.startServer();
        Client client = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and ApplicationServer.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = client.target(ApplicationServer.BASE_URI);
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
    public void testIfProjectResourceGetByIdReturnsAProject() {
        String response = target.path("projects/1").request().get(String.class);
        assertTrue(response.contains("Awesome Project"));
    }




}
