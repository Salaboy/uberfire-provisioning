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

package org.uberfire.provisioning.services.api.itemlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.uberfire.provisioning.services.api.ItemList;
import org.uberfire.provisioning.runtime.providers.Provider;


public class ProviderList implements ItemList<Provider> {

    
    private Provider[] providers;

    public ProviderList() {
    }

    public ProviderList( List<Provider> providers ) {
        this.providers = providers.toArray( new Provider[providers.size()] );
    }

    public ProviderList( Provider[] providers ) {
        this.providers = providers;
    }

    public Provider[] getProviders() {
        return providers;
    }

    public void setProviders( Provider[] providers ) {
        this.providers = providers;
    }

    @Override
    @JsonIgnore
    public List<Provider> getItems() {
        if ( providers == null ) {
            return Collections.emptyList();
        }
        return Arrays.asList( providers );
    }

}
