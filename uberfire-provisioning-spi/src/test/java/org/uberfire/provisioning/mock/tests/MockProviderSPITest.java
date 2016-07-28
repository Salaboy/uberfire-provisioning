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

package org.uberfire.provisioning.mock.tests;

import static java.lang.System.out;
import org.jboss.arquillian.container.test.api.Deployment;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.uberfire.provisioning.mock.MockProviderService;
import org.uberfire.provisioning.mock.MockProviderType;
import org.uberfire.provisioning.mock.MockRuntimeConfiguration;
import org.uberfire.provisioning.runtime.providers.ProviderType;
import static org.jboss.shrinkwrap.api.ShrinkWrap.*;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.*;
import org.uberfire.provisioning.mock.MockProvider;
import org.uberfire.provisioning.mock.MockProviderConfBuilder;
import org.uberfire.provisioning.mock.MockProviderConfiguration;
import org.uberfire.provisioning.mock.MockRuntime;
import org.uberfire.provisioning.mock.MockRuntimeConfBuilder;
import org.uberfire.provisioning.mock.MockRuntimeService;
import org.uberfire.provisioning.mock.external.MockRemoteClient;
import org.uberfire.provisioning.runtime.Runtime;
import org.uberfire.provisioning.runtime.RuntimeEndpoint;
import org.uberfire.provisioning.runtime.RuntimeState;

@RunWith( Arquillian.class )
public class MockProviderSPITest {

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = create( JavaArchive.class )
                .addClass( MockProviderType.class )
                .addClass( MockProvider.class )
                .addClass( MockRuntime.class )
                .addAsManifestResource( INSTANCE, "beans.xml" );
        out.println( jar.toString( true ) );
        return jar;
    }

    @Inject
    @Any
    private Instance<ProviderType> providerTypes;

    @Test
    public void providerTypeRegisteredTest() {
        int i = 0;
        for ( ProviderType pt : providerTypes ) {
            assertEquals( "mock", pt.getProviderTypeName() );
            assertEquals( "1", pt.getVersion() );
            i++;
        }
        assertEquals( 1, i );

    }

    @Test
    public void newMockProviderAndRuntime() {

        ProviderType mockProviderType = providerTypes.iterator().next();

        MockProviderConfiguration config = MockProviderConfBuilder
                .newConfig( "mock" )
                .setHost( "localmock" )
                .setPort( "80" )
                .setMockProperty( "mock conf 1 value" )
                .get();

        MockProvider mockProvider = new MockProvider( config, mockProviderType );

        MockProviderService mockProviderService = new MockProviderService( mockProvider );

        assertNotNull( mockProviderService.getClient() );
        MockRuntimeConfiguration runtimeConfig = MockRuntimeConfBuilder.newConfig()
                .setMockRuntimeProperty( "runtime 1 mock" )
                .setContext( "app" )
                .get();

        Runtime newRuntime = mockProviderService.create( runtimeConfig );
        assertNotNull( newRuntime );
        String runtimeId = newRuntime.getId();

        assertEquals( "mock conf 1 value", ( ( MockProviderConfiguration ) newRuntime.getProvider()
                .getConfig() ).getMockProperty() );

        assertEquals( "runtime 1 mock", ( ( MockRuntimeConfiguration ) newRuntime
                .getConfig() ).getMockRuntimeProperty() );

        RuntimeState state = newRuntime.getState();
        assertNotNull( state );

        assertEquals( "CREATED", state.getStatus() );

        assertNotNull( newRuntime.getInfo() );

        RuntimeEndpoint endpoint = newRuntime.getEndpoint();
        assertNotNull( endpoint );

        assertEquals( "localmock", endpoint.getHost() );
        assertEquals( 80, endpoint.getPort() );
        assertEquals( "app", endpoint.getContext() );

        assertNotNull( newRuntime.getProvider() );

        MockRuntimeService mockRuntimeService = new MockRuntimeService( mockProviderService, newRuntime );

        mockRuntimeService.start();

        newRuntime = mockRuntimeService.getRuntime();

        assertNotNull( newRuntime.getState() );

        state = newRuntime.getState();
        assertNotNull( state );
        assertEquals( "STARTED", state.getStatus() );

        mockRuntimeService.stop();

        state = newRuntime.getState();
        assertNotNull( state );
        assertEquals( "STOPPED", state.getStatus() );

        mockRuntimeService.restart();

        state = newRuntime.getState();
        assertNotNull( state );
        assertEquals( "STARTED", state.getStatus() );

        mockRuntimeService.refresh();

        mockProviderService.destroy( runtimeId );

        MockRemoteClient client = mockProviderService.getClient();
        String runtimeName = client.getDestroyedRuntimesIds().get( newRuntime.getId() );
        assertEquals( "runtime 1 mock", runtimeName );

    }
}
