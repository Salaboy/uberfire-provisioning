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

package org.uberfire.provisioning.pipeline.simple.test;

import java.util.ArrayList;
import java.util.List;
import org.uberfire.provisioning.pipeline.events.AfterPipelineExecutionEvent;
import org.uberfire.provisioning.pipeline.events.AfterStageExecutionEvent;
import org.uberfire.provisioning.pipeline.events.BeforePipelineExecutionEvent;
import org.uberfire.provisioning.pipeline.events.BeforeStageExecutionEvent;
import org.uberfire.provisioning.pipeline.events.PipelineEvent;
import org.uberfire.provisioning.pipeline.events.PipelineEventHandler;

public class MockPipelineEventHandler implements PipelineEventHandler {

    private List<PipelineEvent> events = new ArrayList<>();

    @Override
    public void beforePipelineExecution( BeforePipelineExecutionEvent bpee ) {
        events.add( bpee );
    }

    @Override
    public void afterPipelineExecution( AfterPipelineExecutionEvent apee ) {
        events.add( apee );
    }

    @Override
    public void beforeStageExecution( BeforeStageExecutionEvent bsee ) {
        events.add( bsee );
    }

    @Override
    public void afterStageExecution( AfterStageExecutionEvent asee ) {
        events.add( asee );
    }

    public List<PipelineEvent> getEvents() {
        return events;
    }

}
