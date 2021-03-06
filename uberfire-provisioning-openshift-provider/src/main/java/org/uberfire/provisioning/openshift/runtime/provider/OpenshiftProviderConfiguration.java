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

package org.uberfire.provisioning.openshift.runtime.provider;

import org.uberfire.provisioning.runtime.providers.base.BaseProviderConfiguration;

public class OpenshiftProviderConfiguration extends BaseProviderConfiguration {

    /*
    * This constructor shouldn't be used, The use of KubernetesProviderConfiguration(String name)
    * is strictly recommended. This constructor is here just for the serializers to work correctly
     */
    public OpenshiftProviderConfiguration() {
        super( "", new OpenshiftProviderType().getProvider().getName() );
    }

    public OpenshiftProviderConfiguration( String name ) {
        super( name, new OpenshiftProviderType().getProvider().getName() );
    }

    public void setMasterUrl( String masterUrl ) {
        getProperties().put( "masterUrl", masterUrl );
    }

    public String getMasterUrl() {
        return getProperties().get( "masterUrl" );
    }

    public void setUsername( String username ) {
        getProperties().put( "username", username );
    }

    public String getUsername() {
        return getProperties().get( "username" );
    }

    public void setPassword( String password ) {
        getProperties().put( "password", password );
    }

    public String getPassword() {
        return getProperties().get( "password" );
    }

}
