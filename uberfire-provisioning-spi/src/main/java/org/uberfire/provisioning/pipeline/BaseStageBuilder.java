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

package org.uberfire.provisioning.pipeline;

public abstract class BaseStageBuilder<T extends Stage> {

    protected T stage;

    public BaseStageBuilder<T> withName( String name ) {
        stage.setName( name );
        return this;
    }

    public BaseStageBuilder<T> withRequiredService( Class type ) {
        stage.addRequiredService( type );
        return this;
    }

    public T build() {
        return stage;
    }
}