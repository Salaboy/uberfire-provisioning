/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.provisioning.wildfly.runtime.provider.tests;

import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.uberfire.provisioning.exceptions.ProvisioningException;
import org.uberfire.provisioning.runtime.Runtime;
import org.uberfire.provisioning.runtime.RuntimeConfiguration;
import org.uberfire.provisioning.runtime.base.BaseRuntimeConfiguration;
import org.uberfire.provisioning.runtime.providers.ProviderType;
import org.uberfire.provisioning.wildfly.runtime.provider.base.WildflyProviderConfiguration;
import org.uberfire.provisioning.wildfly.runtime.provider.wildly10.Wildfly10Provider;

import static java.util.logging.Level.*;
import static java.util.logging.Logger.*;
import org.arquillian.cube.CubeController;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.requirement.ArquillianConditionalRunner;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.arquillian.cube.docker.impl.requirement.RequiresDockerMachine;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import static org.junit.Assert.*;
import org.uberfire.provisioning.wildfly.runtime.provider.base.WildflyRuntimeService;
import org.uberfire.provisioning.wildfly.runtime.provider.wildly10.Wildfly10ProviderService;
import org.uberfire.provisioning.wildfly.runtime.provider.wildly10.Wildfly10ProviderType;

@RunWith( ArquillianConditionalRunner.class )
@RequiresDockerMachine( name = "default" )
public class WildflyProviderRuntimeTest {

    private static final String CONTAINER = "swarm";

    @HostIp
    String ip;

    @ArquillianResource
    private CubeController cc;

    @Test
    @InSequence( 0 )
    public void shouldBeAbleToInjectControllerTest() {
        assertNotNull( cc );
    }

    @Test
    @InSequence( 1 )
    public void shouldBeAbleToCreateAndStartTest() {
        cc.create( CONTAINER );
        cc.start( CONTAINER );
    }

    @Test
    @InSequence( 2 )
    public void newWildflyProviderWithWildflyRunningTest() throws InterruptedException {
        System.out.println( " IP: " + ip );
        ProviderType wildflyProviderType = new Wildfly10ProviderType();

        WildflyProviderConfiguration config = new WildflyProviderConfiguration( "wildfly @ 9990" );

        config.setHost( ip );
        config.setManagementPort( "9990" );
        config.setPort( "8080" );
        config.setUser( "admin" );
        config.setPassword( "Admin#70365" );

        Wildfly10Provider wildflyProvider = new Wildfly10Provider( config, wildflyProviderType );
        Wildfly10ProviderService providerService = new Wildfly10ProviderService( wildflyProvider );

        assertNotNull( providerService.getWildfly() );
        RuntimeConfiguration runtimeConfig = new BaseRuntimeConfiguration();

        WebArchive archive = ShrinkWrap.create( WebArchive.class, "test.war" );
        archive.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );

        new ZipExporterImpl( archive ).exportTo( new File( archive.getName() ), true );

        runtimeConfig.getProperties().put( "warPath", archive.getName() );

        Runtime newRuntime = null;

        try {
            newRuntime = providerService.create( runtimeConfig );
        } catch ( ProvisioningException ex ) {
            getLogger( WildflyProviderRuntimeTest.class.getName() ).log( SEVERE, null, ex );
        }

        assertNotNull( newRuntime );
        assertNotNull( newRuntime.getId() );

        WildflyRuntimeService wildflyRuntimeService = new WildflyRuntimeService( providerService, newRuntime );

        wildflyRuntimeService.refresh();

        newRuntime = wildflyRuntimeService.getRuntime();

        assertNotNull( newRuntime );
        // TODO: check state and info
    }

    @Test
    @InSequence( 3 )
    public void shouldBeAbleToStopAndDestroyTest() {
        cc.stop( CONTAINER );
        cc.destroy( CONTAINER );
    }

}
