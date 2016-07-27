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

package org.uberfire.provisioning.runtime;

import org.uberfire.provisioning.runtime.providers.Provider;

/**
 * <p>
 * This class represent a Runtime (Docker Image running or a WAR deployed into a
 * server)
 * It also allows you to interact with the runtime state executing operations
 * such as start, stop, restart
 */

public interface Runtime {

    String getId();

    void setId( String id );

    RuntimeEndpoint getEndpoint();

    void setEndpoint( RuntimeEndpoint endpoint );

    void setConfig( RuntimeConfiguration config );

    RuntimeConfiguration getConfig();

    void setState( RuntimeState state );

    RuntimeState getState();

    void setInfo( RuntimeInfo info );

    RuntimeInfo getInfo();

    Provider getProvider();

    void setProvider( Provider provider );

}
