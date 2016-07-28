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

package org.uberfire.provisioning.mock.external;

import java.util.HashMap;
import java.util.Map;
import static java.util.UUID.randomUUID;

public class MockRemoteClient {

    private final Map<String, String> remoteRuntimesByName = new HashMap<>();

    private final Map<String, String> remoteRuntimesStates = new HashMap<>();

    private final Map<String, String> destroyedRuntimesIds = new HashMap<>();
    
    private final String remoteClientEndpoint;
    
    private final int remoteClientPort;

    public MockRemoteClient( String remoteClientEndpoint, int remoteClientPort ) {
        this.remoteClientEndpoint = remoteClientEndpoint;
        this.remoteClientPort = remoteClientPort;
    }


    public String getState( String name ) {
        return remoteRuntimesStates.get( name );
    }
    
    public String getEndpoint(){
        return remoteClientEndpoint;
    }
    
    public int getPort(){
        return remoteClientPort;
    }

    public String create( String name ) {
        String shortId = randomUUID().toString().substring( 0, 12 );
        remoteRuntimesByName.put( name, shortId );
        remoteRuntimesStates.put( shortId, "CREATED" );
        return shortId;
    }

    public boolean destroy( String id ) {
        String findName = "";
        for ( String name : remoteRuntimesByName.keySet() ) {
            if ( remoteRuntimesByName.get( name ).equals( id ) ) {
                findName = name;
            }
        }
        if ( !findName.equals( "" ) ) {
            remoteRuntimesByName.remove( findName );
            remoteRuntimesStates.remove( id );
            destroyedRuntimesIds.put( id, findName );
            return true;
        } else {
            return false;
        }
    }

    public void start( String id ) {
        remoteRuntimesStates.put( id, "STARTED" );
    }

    public void stop( String id ) {
        remoteRuntimesStates.put( id, "STOPPED" );
    }

    public void restart( String id ) {
        remoteRuntimesStates.put( id, "STARTED" );
    }

    public Map<String, String> getDestroyedRuntimesIds() {
        return destroyedRuntimesIds;
    }
    
    

}
