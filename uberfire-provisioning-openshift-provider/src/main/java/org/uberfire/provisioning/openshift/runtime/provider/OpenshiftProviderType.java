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

import javax.enterprise.context.ApplicationScoped;

import org.uberfire.provisioning.runtime.providers.base.BaseProviderType;

@ApplicationScoped
@Openshift
public class OpenshiftProviderType extends BaseProviderType {

    public OpenshiftProviderType() {
        super( "openshift", "1", OpenshiftProvider.class, OpenshiftProviderService.class, OpenshiftRuntimeService.class );
    }

    public OpenshiftProviderType( String providerName, String version, Class provider, Class providerService, Class runtimeService ) {
        super( providerName, version, provider, providerService, runtimeService );
    }
    

}
