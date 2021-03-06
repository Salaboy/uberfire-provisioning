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

package org.uberfire.provisioning.services.api;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.uberfire.provisioning.runtime.RuntimeConfiguration;
import org.uberfire.provisioning.runtime.providers.ProviderConfiguration;
import org.uberfire.provisioning.services.exceptions.BusinessException;

import static javax.ws.rs.core.MediaType.*;
import org.uberfire.provisioning.services.api.itemlist.ProviderList;
import org.uberfire.provisioning.services.api.itemlist.ProviderTypeList;
import org.uberfire.provisioning.services.api.itemlist.RuntimeList;

@Path("runtime")
public interface RuntimeProvisioningService {

    @GET
    @Consumes(value = APPLICATION_JSON)
    @Produces(value = APPLICATION_JSON)
    @Path("providertypes")
    ProviderTypeList getAllProviderTypes() throws BusinessException;

    @GET
    @Produces(value = APPLICATION_JSON)
    @Path("providers")
    ProviderList getAllProviders() throws BusinessException;

    @POST
    @Consumes(value = APPLICATION_JSON)
    @Path("providers")
    void registerProvider( @NotNull ProviderConfiguration conf ) throws BusinessException;

    @DELETE
    @Path("providers")
    void unregisterProvider( @FormParam(value = "name") String name ) throws BusinessException;

    @POST
    @Path("runtimes/")
    @Consumes(value = APPLICATION_JSON)
    String newRuntime( @NotNull RuntimeConfiguration conf ) throws BusinessException;
    
    @DELETE
    @Path("runtimes/{id}")
    void destroyRuntime(@PathParam(value = "id") String runtimeId ) throws BusinessException;

    @GET
    @Produces(value = APPLICATION_JSON)
    @Path("runtimes/")
    RuntimeList getAllRuntimes() throws BusinessException;


    @POST
    @Path("runtimes/{id}/start")
    void startRuntime( @PathParam(value = "id") String runtimeId ) throws BusinessException;

    @POST
    @Path("runtimes/{id}/stop")
    void stopRuntime( @PathParam(value = "id") String runtimeId ) throws BusinessException;

    @POST
    @Path("runtimes/{id}/restart")
    void restartRuntime( @PathParam(value = "id") String runtimeId ) throws BusinessException;

}
