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

package org.uberfire.provisioning.source.github;

import org.uberfire.provisioning.source.Repository;

import static java.util.UUID.*;

/**
 * @author salaboy
 */
public class GitHubRepository implements Repository {

    private String id;
    private String URI;
    private String name;
    private String type;
    private String branch;
    private boolean bare = false;

    public GitHubRepository() {
        this.type = "GitHub";
        this.id = randomUUID().toString().substring( 0, 12 );
    }

    public GitHubRepository( String name ) {
        this();
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getURI() {
        return URI;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isBare() {
        return bare;
    }

    @Override
    public void setURI( String URI ) {
        this.URI = URI;
    }

    @Override
    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public void setType( String type ) {
        this.type = type;
    }

    @Override
    public void setBare( boolean bare ) {
        this.bare = bare;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch( String branch ) {
        this.branch = branch;
    }

}
