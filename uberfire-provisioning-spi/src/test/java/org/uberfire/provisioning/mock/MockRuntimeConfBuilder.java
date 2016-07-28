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

public class MockRuntimeConfBuilder {

    private static MockRuntimeConfBuilder instance;
    private static MockRuntimeConfiguration config;

    private MockRuntimeConfBuilder() {
    }

    public static MockRuntimeConfBuilder newConfig() {
        instance = new MockRuntimeConfBuilder();
        config = new MockRuntimeConfiguration();
        return instance;
    }

    public MockRuntimeConfBuilder setMockRuntimeProperty( String mockRuntimeProperty ) {
        config.setMockRuntimeProperty( mockRuntimeProperty );
        return instance;
    }

    public MockRuntimeConfBuilder setContext( String context ) {
        config.setContext( context );
        return instance;
    }

    public MockRuntimeConfiguration get() {
        if ( !config.getMockRuntimeProperty().equals( "" ) && !config.getContext().equals( "" ) ) {
            return config;
        } else {
            throw new IllegalStateException( "MockRuntimeProperty and Context are required!" );
        }
    }
}
