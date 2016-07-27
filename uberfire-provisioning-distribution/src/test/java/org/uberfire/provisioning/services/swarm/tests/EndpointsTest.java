
package org.uberfire.provisioning.services.swarm.tests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.uberfire.provisioning.runtime.providers.ProviderType;
import org.uberfire.provisioning.services.api.RuntimeProvisioningService;
import org.uberfire.provisioning.services.api.itemlist.ProviderList;
import org.uberfire.provisioning.services.api.itemlist.ProviderTypeList;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

@RunWith( Arquillian.class )
public class EndpointsTest {

    private final String APP_URL = "http://localhost:8080/";

    @Deployment( testable = true )
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create( JAXRSArchive.class );
        deployment.addPackages( true, "com.google.common" );
        deployment.setContextRoot( "/api" );
        deployment.addAllDependencies();
        return deployment;
    }
//
//    @Test
//    @RunAsClient
//    public void checkService() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target( APP_URL + "runtime/providertypes" );
//        Response response = target.request( MediaType.APPLICATION_JSON ).get();
//        Assert.assertEquals( Response.Status.OK.getStatusCode(), response.getStatus() );
//
//        String responseAsString = response.readEntity( String.class );
//        assertNotNull( responseAsString );
//
//    }

    @Test
    @RunAsClient
    public void checkService() {
        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target( "http://localhost:8080/" );
        final ResteasyWebTarget rtarget = ( ResteasyWebTarget ) target;
        RuntimeProvisioningService remoteService = rtarget.proxy( RuntimeProvisioningService.class );

//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target( APP_URL + "runtime/providertypes" );
//        Response response = target.request( MediaType.APPLICATION_JSON ).get();
//        Assert.assertEquals( Response.Status.OK.getStatusCode(), response.getStatus() );
//
//        ProviderTypeList result = response.readEntity( ProviderTypeList.class );
        ProviderTypeList allProviderTypes = remoteService.getAllProviderTypes();
        assertNotNull( allProviderTypes );
        assertEquals( 4, allProviderTypes.getItems().size() );
        for ( ProviderType pt : allProviderTypes.getItems() ) {
            System.out.println( " pt: " + pt );
        }
        ProviderList allProviders = remoteService.getAllProviders();

        assertNotNull( allProviders );
        assertEquals( 0, allProviders.getItems().size() );

    }

}
