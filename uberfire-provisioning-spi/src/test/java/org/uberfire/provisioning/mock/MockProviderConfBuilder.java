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

public class MockProviderConfBuilder {

    private static MockProviderConfBuilder instance;
    private static MockProviderConfiguration config;

    private MockProviderConfBuilder() {
    }

    public static MockProviderConfBuilder newConfig( String providerName ) {
        instance = new MockProviderConfBuilder();
        config = new MockProviderConfiguration( providerName );
        return instance;
    }

    public MockProviderConfBuilder setMockProperty( String mockProperty ) {
        config.setMockProperty( mockProperty );
        return instance;
    }

    public MockProviderConfBuilder setHost( String host ) {
        config.setHost( host );
        return instance;
    }

    public MockProviderConfBuilder setPort( String port ) {
        config.setPort( port );
        return instance;
    }

    public MockProviderConfiguration get() {
        if ( !config.getHost().equals( "" ) && !config.getPort().equals( "" ) && !config.getMockProperty().equals( "" ) ) {
            return config;
        } else {
            throw new IllegalStateException( " The Host, Port and MockProperty are required to be set" );
        }
    }
}
