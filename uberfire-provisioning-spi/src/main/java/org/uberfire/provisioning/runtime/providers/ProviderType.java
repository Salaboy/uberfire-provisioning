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
package org.uberfire.provisioning.runtime.providers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

/**
 * This class provides the definition for a ProviderType.
 * Different provider types can be implemented and discovered at runtime.
 */
@JsonTypeInfo( use = CLASS, include = WRAPPER_OBJECT )
public interface ProviderType {

    String getProviderTypeName();

    String getVersion();

    Class getProvider();

    void setProviderTypeName( String providerTypeName );

    void setVersion( String version );

    void setProvider( Class providerClass );

    Class getProviderService();

    void setProviderService( Class providerService );

    Class getRuntimeService();

    void setRuntimeService( Class runtimeService );

}
