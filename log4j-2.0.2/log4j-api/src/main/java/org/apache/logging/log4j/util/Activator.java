/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.util;

import java.net.URL;
import java.security.Permission;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * OSGi bundle activator. Used for locating an implementation of
 * {@link org.apache.logging.log4j.spi.LoggerContextFactory} et al. that have corresponding
 * {@code META-INF/log4j-provider.properties} files. As with all OSGi BundleActivator classes, this class is not for
 * public use and is only useful in an OSGi framework environment.
 */
public class Activator implements BundleActivator, SynchronousBundleListener {

    private static final SecurityManager SECURITY_MANAGER = System.getSecurityManager();

    private static final Logger LOGGER = StatusLogger.getLogger();

    private static void checkPermission(final Permission permission) {
        if (SECURITY_MANAGER != null) {
            SECURITY_MANAGER.checkPermission(permission);
        }
    }

    private void loadProvider(final Bundle bundle) {
        if (bundle.getState() == Bundle.UNINSTALLED) {
            return;
        }
        try {
            checkPermission(new AdminPermission(bundle, AdminPermission.RESOURCE));
            checkPermission(new AdaptPermission(BundleWiring.class.getName(), bundle, AdaptPermission.ADAPT));
            loadProvider(bundle.adapt(BundleWiring.class));
        } catch (final SecurityException e) {
            LOGGER.debug("Cannot access bundle [{}] contents. Ignoring.", bundle.getSymbolicName(), e);
        } catch (final Exception e) {
            LOGGER.warn("Problem checking bundle {} for Log4j 2 provider.", bundle.getSymbolicName(), e);
        }
    }

    private void loadProvider(BundleWiring provider) {
        final List<URL> urls = provider.findEntries("META-INF", "log4j-provider.properties", 0);
        for (final URL url : urls) {
            ProviderUtil.loadProvider(url, provider.getClassLoader());
        }
    }

    @Override
    public void start(final BundleContext context) throws Exception {
        final BundleWiring self = context.getBundle().adapt(BundleWiring.class);
        final List<BundleWire> required = self.getRequiredWires(LoggerContextFactory.class.getName());
        for (BundleWire wire : required) {
            loadProvider(wire.getProviderWiring());
        }
        context.addBundleListener(this);
        final Bundle[] bundles = context.getBundles();
        for (final Bundle bundle : bundles) {
            loadProvider(bundle);
        }
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        context.removeBundleListener(this);
    }

    @Override
    public void bundleChanged(final BundleEvent event) {
        switch (event.getType()) {
            case BundleEvent.STARTED:
                // FIXME: LogManager won't see this update if it happens after LogManager is loaded
                loadProvider(event.getBundle());
                break;

            default:
                break;
        }
    }

}
