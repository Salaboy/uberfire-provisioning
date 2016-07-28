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

import org.uberfire.provisioning.runtime.providers.base.BaseProviderConfiguration;

public class MockProviderConfiguration extends BaseProviderConfiguration {

    public MockProviderConfiguration() {
        super( "", new MockProviderType().getProvider().getName() );
    }

    public MockProviderConfiguration( String name ) {
        super( name, new MockProviderType().getProvider().getName() );
    }

    public void setHost( String host ) {
        getProperties().put( "host", host );
    }

    public String getHost() {
        return getProperties().get( "host" );
    }

    public void setPort( String port ) {
        getProperties().put( "port", port );
    }

    public String getPort() {
        return getProperties().get( "port" );
    }

    public void setMockProperty( String mockProperty ) {
        getProperties().put( "mockProperty", mockProperty );
    }

    public String getMockProperty() {
        return getProperties().get( "mockProperty" );
    }
}
