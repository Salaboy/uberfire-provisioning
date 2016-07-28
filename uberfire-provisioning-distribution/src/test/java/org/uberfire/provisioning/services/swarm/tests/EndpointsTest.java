
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
import org.uberfire.provisioning.docker.runtime.provider.DockerProviderConfBuilder;
import org.uberfire.provisioning.local.runtime.provider.LocalProviderConfBuilder;
import org.uberfire.provisioning.local.runtime.provider.LocalRuntimeConfBuilder;
import org.uberfire.provisioning.local.runtime.provider.LocalRuntimeConfiguration;
import org.uberfire.provisioning.openshift.runtime.provider.OpenshiftProviderConfBuilder;
import org.uberfire.provisioning.pipeline.Pipeline;
import org.uberfire.provisioning.pipeline.PipelineTemplate;
import org.uberfire.provisioning.runtime.providers.ProviderConfiguration;
import org.uberfire.provisioning.services.api.PipelineService;
import org.uberfire.provisioning.services.api.RuntimeProvisioningService;
import org.uberfire.provisioning.services.api.itemlist.PipelineList;
import org.uberfire.provisioning.services.api.itemlist.PipelineTemplateList;
import org.uberfire.provisioning.services.api.itemlist.ProviderList;
import org.uberfire.provisioning.services.api.itemlist.ProviderTypeList;
import org.uberfire.provisioning.services.api.itemlist.RuntimeList;
import org.uberfire.provisioning.wildfly.runtime.provider.base.WildflyProviderConfBuilder;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

@RunWith( Arquillian.class )
public class EndpointsTest {

    private final String APP_URL = "http://localhost:8080/";

    @Deployment( testable = true )
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create( JAXRSArchive.class );
        deployment.addPackages( true, "com.google.common" );
        deployment.addClass( PrintOutStage.class );
        deployment.setContextRoot( "/api" );
        deployment.addAllDependencies();
        return deployment;
    }

    @Test
    @RunAsClient
    public void checkRuntimeService() {
        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target( APP_URL );
        final ResteasyWebTarget rtarget = ( ResteasyWebTarget ) target;
        RuntimeProvisioningService remoteService = rtarget.proxy( RuntimeProvisioningService.class );

        ProviderTypeList allProviderTypes = remoteService.getAllProviderTypes();
        assertNotNull( allProviderTypes );
        assertEquals( 4, allProviderTypes.getItems().size() );
        
        ProviderList allProviders = remoteService.getAllProviders();

        assertNotNull( allProviders );
        assertEquals( 0, allProviders.getItems().size() );
        
        ProviderConfiguration localConf = LocalProviderConfBuilder.newConfig("local runtime").get();
        remoteService.registerProvider( localConf );
        
        ProviderConfiguration wildfly10Conf = WildflyProviderConfBuilder
                                                                .newConfig("wildfly on localhost")
                                                                .setHost("localhost")
                                                                .setPort( "8080")
                                                                .setManagementPort("9990")
                                                                .setUser("admin")
                                                                .setPassword("admin")
                                                                .get();
        remoteService.registerProvider( wildfly10Conf );
        
        ProviderConfiguration openshiftConf = OpenshiftProviderConfBuilder
                                                                .newConfig("openshift")
                                                                .get();
        
        remoteService.registerProvider( openshiftConf );
        
        ProviderConfiguration dockerConf = DockerProviderConfBuilder
                                                                .newConfig("docker with local connection")
                                                                .get();

        remoteService.registerProvider( dockerConf );
        
        allProviders = remoteService.getAllProviders();

        assertNotNull( allProviders );
        
        assertEquals( 4, allProviders.getItems().size() );
        
        LocalRuntimeConfiguration localRuntimeConfig = LocalRuntimeConfBuilder.newConfig().setProviderName("local runtime")
                .setJar( "../extras/sample-war/target/sample-war-1.0-SNAPSHOT-swarm.jar" )
                .get();
        
        
        RuntimeList allRuntimes = remoteService.getAllRuntimes();
        assertEquals( 0, allRuntimes.getItems().size() );
        
        String newRuntimeId = remoteService.newRuntime( localRuntimeConfig );
        
        assertNotNull(newRuntimeId);
        
        allRuntimes = remoteService.getAllRuntimes();
        assertEquals( 1, allRuntimes.getItems().size() );
        
        remoteService.destroyRuntime( newRuntimeId );
        
        
        
    }
    
    @Test
    @RunAsClient
    public void checkPipelineService() {
        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client.target( APP_URL );
        final ResteasyWebTarget rtarget = ( ResteasyWebTarget ) target;
        PipelineService remoteService = rtarget.proxy( PipelineService.class );
        
        PipelineList allPipelines = remoteService.getAllPipelines();
        assertEquals( 0, allPipelines.getItems().size());
        
        PipelineTemplate template = PipelineTemplate.builder()
                .newTemplate( "My template" )
                .withPrefix( "template1" )
                .addStage( "Print Out Stage", PrintOutStage.class )
                .build();
        
        PipelineTemplateList allPipelineTemplates = remoteService.getAllPipelineTemplates();
        assertEquals( 0, allPipelineTemplates.getItems().size());
        
        remoteService.registerPipelineTemplate(template);
        
        allPipelineTemplates = remoteService.getAllPipelineTemplates();
        assertEquals( 1, allPipelineTemplates.getItems().size());
        
        PipelineTemplate pipelineTemplateById = remoteService.getPipelineTemplateById( "My template" );
        assertNotNull(pipelineTemplateById);
        // Create Builder for stages based on template
        PrintOutStage.PrintOutStageBuilder printOutBuilder = PrintOutStage.builder().withName( template.getStages().get( 0 ).getName() );
        
        Pipeline pipeline = Pipeline.builderFromTemplate( pipelineTemplateById )
                .withName( "My Pipeline" )
                .withStage( printOutBuilder.build() )
                .build();
        
        
        String newPipelineId = remoteService.newPipeline( pipeline );
        
        allPipelines = remoteService.getAllPipelines();
        assertEquals( 1, allPipelines.getItems().size());
        
        remoteService.runPipeline( newPipelineId );
        
        
        
        
        
    }
    

}
