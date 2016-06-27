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

import javax.xml.bind.annotation.XmlRootElement;

import org.uberfire.provisioning.pipeline.PipelineContext;
import org.uberfire.provisioning.pipeline.Stage;

import java.util.List;

@XmlRootElement
public class PrintOutStage2 implements Stage {

    private String name;

    public PrintOutStage2() {
    }

    public PrintOutStage2( String name ) {
        this.name = name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute( PipelineContext context ) {
        List<String> messages = ( List<String> ) context.getServices().get( "messages" );
        messages.add( ">>> Message Version 2: " + context.getData().get( "message" ) );
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof Stage ) ) {
            return false;
        }

        final Stage that = (Stage) o;

        return getName() != null ? getName().equals( that.getName() ) : that.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }


}
