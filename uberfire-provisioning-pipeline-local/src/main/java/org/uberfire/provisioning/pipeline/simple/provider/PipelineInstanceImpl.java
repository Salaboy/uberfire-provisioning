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

package org.uberfire.provisioning.pipeline.simple.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.uberfire.provisioning.pipeline.events.AfterPipelineExecutionEvent;
import org.uberfire.provisioning.pipeline.events.AfterStageExecutionEvent;
import org.uberfire.provisioning.pipeline.events.BeforePipelineExecutionEvent;
import org.uberfire.provisioning.pipeline.events.BeforeStageExecutionEvent;
import org.uberfire.provisioning.pipeline.events.PipelineEventHandler;
import org.uberfire.provisioning.pipeline.Pipeline;
import org.uberfire.provisioning.pipeline.Stage;
import org.uberfire.provisioning.pipeline.PipelineInstance;
import org.uberfire.provisioning.pipeline.PipelineDataContext;

public class PipelineInstanceImpl implements PipelineInstance {

    private final Pipeline pipeline;

    private Map<Class, Object> services;

    private Set<PipelineEventHandler> handlers;

    public PipelineInstanceImpl( Pipeline pipeline ) {
        this.pipeline = pipeline;
    }

    @Override
    public void registerService( Class type, Object service ) {
        if ( services == null ) {
            services = new HashMap<>();
        }
        services.put( type, service );
    }

    @Override
    public PipelineDataContext execute() {

        if ( !checkRegisteredService() ) {
            throw new IllegalStateException( "You need to register all the required services before running the Pipeline" );
        }
        if ( handlers == null ) {
            handlers = new HashSet<>();
            handlers.add( new DefaultPipelineEventHandler() );
        }
        for ( PipelineEventHandler h : handlers ) {
            h.beforePipelineExecution( new BeforePipelineExecutionEvent( pipeline ) );
        }

        PipelineDataContext data = new PipelineDataContextImpl();
        for ( Stage s : pipeline.getStages() ) {
            for ( PipelineEventHandler h : handlers ) {
                h.beforeStageExecution( new BeforeStageExecutionEvent( pipeline, s ) );
            }
            s.execute( this, data );
            for ( PipelineEventHandler h : handlers ) {
                h.afterStageExecution( new AfterStageExecutionEvent( pipeline, s ) );
            }
        }
        for ( PipelineEventHandler h : handlers ) {
            h.afterPipelineExecution( new AfterPipelineExecutionEvent( pipeline ) );
        }
        return data;

    }

    @Override
    public void registerEventHandler( PipelineEventHandler handler ) {
        if ( handlers == null ) {
            handlers = new HashSet<>();
        }
        handlers.add( handler );
    }

    @Override
    public <T> T getService( Class<T> type ) {
        return type.cast( services.get( type ) );
    }

    @Override
    public Pipeline getPipeline() {
        return pipeline;
    }

    private boolean checkRegisteredService() {
        if ( pipeline.getRequiredServices() != null ) {
            for ( Class type : pipeline.getRequiredServices() ) {
                if ( services.get( type ) == null ) {
                    System.out.println( "Error: The Service type: " + type + " is required and it is not registered" );
                    return false;
                }
            }
        }
        return true;
    }

}
