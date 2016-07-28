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

import org.uberfire.provisioning.runtime.Runtime;
import org.uberfire.provisioning.runtime.RuntimeService;
import org.uberfire.provisioning.runtime.base.BaseRuntimeEndpoint;
import org.uberfire.provisioning.runtime.providers.ProviderService;

public class MockRuntimeService implements RuntimeService {
    
    private MockProviderService providerService;
    private org.uberfire.provisioning.runtime.Runtime runtime;
    
    public MockRuntimeService( ProviderService providerService, Runtime runtime ) {
        if ( !( providerService instanceof MockProviderService ) ) {
            throw new IllegalArgumentException( "Wrong provider! set: " + providerService.getClass() + " expected: MockProvider" );
        }
        this.runtime = runtime;
        this.providerService = ( MockProviderService ) providerService;
        
    }
    
    @Override
    public void start() {
        providerService.getClient().start( runtime.getId() );
        String state = providerService.getClient().getState( runtime.getId() );
        runtime.setState( new MockRuntimeState( state, String.valueOf( System.currentTimeMillis() ) ) );
    }
    
    @Override
    public void stop() {
        providerService.getClient().stop( runtime.getId() );
        String state = providerService.getClient().getState( runtime.getId() );
        runtime.setState( new MockRuntimeState( state, String.valueOf( System.currentTimeMillis() ) ) );
    }
    
    @Override
    public void restart() {
        providerService.getClient().restart( runtime.getId() );
        String state = providerService.getClient().getState( runtime.getId() );
        runtime.setState( new MockRuntimeState( state, String.valueOf( System.currentTimeMillis() ) ) );
    }
    
    @Override
    public void refresh() {
        String state = providerService.getClient()
                .getState( ( ( MockRuntimeConfiguration ) runtime.getConfig() ).getMockRuntimeProperty() );
        runtime.setState( new MockRuntimeState( state, String.valueOf( System.currentTimeMillis() ) ) );
        runtime.setEndpoint( new BaseRuntimeEndpoint( providerService.getClient().getEndpoint(),
                providerService.getClient().getPort(),
                ( ( MockRuntimeConfiguration ) runtime.getConfig() ).getMockRuntimeProperty() ) );
    }
    
    @Override
    public ProviderService getProviderService() {
        return this.providerService;
    }
    
    @Override
    public void setProviderService( ProviderService providerService ) {
        this.providerService = ( MockProviderService ) providerService;
    }
    
    @Override
    public Runtime getRuntime() {
        return this.runtime;
    }
    
    @Override
    public void setRuntime( Runtime runtime ) {
        this.runtime = runtime;
    }
    
}
