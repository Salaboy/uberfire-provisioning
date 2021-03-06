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

package org.uberfire.provisioning.services.backend.impl;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;

import org.uberfire.provisioning.services.api.PipelineService;
import org.uberfire.provisioning.services.api.backend.PipelineServiceBackend;
import org.uberfire.provisioning.services.exceptions.BusinessException;
import org.uberfire.provisioning.pipeline.Pipeline;

@Service
@ApplicationScoped
public class PipelineServiceBackendImpl implements PipelineServiceBackend {

    private PipelineService pipelineService;

    public PipelineServiceBackendImpl() {
    }

    @Inject
    public PipelineServiceBackendImpl( final PipelineService pipelineService ) {
        this.pipelineService = pipelineService;
    }

    @Override
    public List<Pipeline> getAllPipelines() throws BusinessException {
        return pipelineService.getAllPipelines().getItems();
    }

    @Override
    public String newPipeline( Pipeline pipeline ) throws BusinessException {
        return pipelineService.newPipeline( pipeline );
    }

    @Override
    public void runPipeline( final String id ) throws BusinessException {
        pipelineService.runPipeline( id );
    }

}
