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

package org.uberfire.provisioning.mock;

import org.uberfire.provisioning.exceptions.ProvisioningException;
import org.uberfire.provisioning.mock.external.MockRemoteClient;
import org.uberfire.provisioning.runtime.Runtime;
import org.uberfire.provisioning.runtime.RuntimeConfiguration;
import org.uberfire.provisioning.runtime.base.BaseRuntimeEndpoint;
import org.uberfire.provisioning.runtime.providers.ProviderService;

public class MockProviderService implements ProviderService {

    private final MockProvider provider;
    private final MockRemoteClient client;

    public MockProviderService( MockProvider provider ) {
        this.provider = provider;
        String host = ( ( MockProviderConfiguration ) provider.getConfig() ).getHost();
        String port = ( ( MockProviderConfiguration ) provider.getConfig() ).getPort();
        this.client = new MockRemoteClient( host, Integer.valueOf( port ) );
    }

    @Override
    public Runtime create( RuntimeConfiguration runtimeConfig ) throws ProvisioningException {
        String runtimeId = client.create( ( ( MockRuntimeConfiguration ) runtimeConfig ).getMockRuntimeProperty() );
        MockRuntime mockRuntime = new MockRuntime( runtimeId, runtimeConfig, provider );
        String state = client.getState( runtimeId );
        mockRuntime.setState( new MockRuntimeState( state, String.valueOf( System.currentTimeMillis() ) ) );
        mockRuntime.setEndpoint( new BaseRuntimeEndpoint( client.getEndpoint(), client.getPort(), ( ( MockRuntimeConfiguration ) runtimeConfig ).getContext() ) );
        return mockRuntime;
    }

    @Override
    public boolean destroy( String runtimeId ) throws ProvisioningException {
        return client.destroy( runtimeId );
        
    }

    public MockRemoteClient getClient() {
        return client;
    }

}
