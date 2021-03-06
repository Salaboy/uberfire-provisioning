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

package org.uberfire.provisioning.pipeline.events;

import org.uberfire.provisioning.pipeline.Pipeline;
import org.uberfire.provisioning.pipeline.Stage;

public class BeforeStageExecutionEvent implements PipelineEvent {

    private Pipeline pipeline;
    private Stage stage;

    public BeforeStageExecutionEvent( final Pipeline pipeline,
                                      final Stage stage ) {
        this.pipeline = pipeline;
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }
}
