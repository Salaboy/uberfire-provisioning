/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uberfire.provisioning.kubernetes.runtime.provider;

import org.uberfire.provisioning.runtime.spi.RuntimeConfiguration;
import org.uberfire.provisioning.runtime.spi.RuntimeInfo;
import org.uberfire.provisioning.runtime.spi.RuntimeState;
import org.uberfire.provisioning.runtime.spi.base.BaseRuntime;
import org.uberfire.provisioning.runtime.spi.providers.Provider;

/**
 *
 * @author salaboy
 */
public class KubernetesRuntime extends BaseRuntime {

    public KubernetesRuntime(String id, RuntimeConfiguration config, Provider provider) {
        super(id, config, provider);
        if (!(provider instanceof KubernetesProvider)) {
            throw new IllegalArgumentException("Wrong provider! set: " + provider.getClass() + " expected: DockerProvider");
        }
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void restart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RuntimeInfo getInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RuntimeState getState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}