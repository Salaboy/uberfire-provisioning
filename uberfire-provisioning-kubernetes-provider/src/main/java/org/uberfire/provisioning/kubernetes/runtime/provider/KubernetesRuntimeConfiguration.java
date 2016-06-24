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

package org.uberfire.provisioning.kubernetes.runtime.provider;

import org.uberfire.provisioning.runtime.base.BaseRuntimeConfiguration;

public class KubernetesRuntimeConfiguration extends BaseRuntimeConfiguration {

    public void setNamespace( String namespace ) {
        getProperties().put( "namespace", namespace );
    }

    public void setReplicationController( String replicationController ) {
        getProperties().put( "replicationController", replicationController );
    }

    public void setLabel( String label ) {
        getProperties().put( "label", label );
    }

    public void setInternalPort( String internalPort ) {
        getProperties().put( "internalPort", internalPort );
    }
    
    public void setServiceName( String serviceName ) {
        getProperties().put( "serviceName", serviceName );
    }

    public void setImage( String image ) {
        getProperties().put( "image", image );
    }

}
