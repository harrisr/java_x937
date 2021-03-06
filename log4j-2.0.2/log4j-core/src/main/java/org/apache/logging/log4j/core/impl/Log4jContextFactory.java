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
package org.apache.logging.log4j.core.impl;

import java.net.URI;

import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

/**
 * Factory to locate a ContextSelector and then load a LoggerContext.
 */
public class Log4jContextFactory implements LoggerContextFactory {

    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    private ContextSelector selector;

    /**
     * Initializes the ContextSelector from system property {@link Constants#LOG4J_CONTEXT_SELECTOR}.
     */
    public Log4jContextFactory() {
        this(createContextSelector());
    }

    /**
     * Initializes this factory's ContextSelector with the specified selector.
     * @param selector the selector to use
     */
    public Log4jContextFactory(final ContextSelector selector) {
        this.selector = selector;
    }

    private static ContextSelector createContextSelector() {
        final String sel = PropertiesUtil.getProperties().getStringProperty(Constants.LOG4J_CONTEXT_SELECTOR);
        if (sel != null) {
            try {
                return Loader.newCheckedInstanceOf(sel, ContextSelector.class);
            } catch (final Exception ex) {
                LOGGER.error("Unable to create context {}", sel, ex);
            }
        }
        return new ClassLoaderContextSelector();
    }

    /**
     * Loads the LoggerContext using the ContextSelector.
     * @param fqcn The fully qualified class name of the caller.
     * @param loader The ClassLoader to use or null.
     * @param currentContext If true returns the current Context, if false returns the Context appropriate
     * for the caller if a more appropriate Context can be determined.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @return The LoggerContext.
     */
    @Override
    public LoggerContext getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
                                    final boolean currentContext) {
        final LoggerContext ctx = selector.getContext(fqcn, loader, currentContext);
        ctx.setExternalContext(externalContext);
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            ctx.start();
        }
        return ctx;
    }

    /**
     * Loads the LoggerContext using the ContextSelector.
     * @param fqcn The fully qualified class name of the caller.
     * @param loader The ClassLoader to use or null.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @param currentContext If true returns the current Context, if false returns the Context appropriate
     * for the caller if a more appropriate Context can be determined.
     * @param source The configuration source.
     * @return The LoggerContext.
     */
    public LoggerContext getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
                                    final boolean currentContext, final ConfigurationSource source) {
        final LoggerContext ctx = selector.getContext(fqcn, loader, currentContext, null);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            if (source != null) {
                ContextAnchor.THREAD_CONTEXT.set(ctx);
                final Configuration config = ConfigurationFactory.getInstance().getConfiguration(source);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration {}", ctx.getName(), source);
                ctx.start(config);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                ctx.start();
            }
        }
        return ctx;
    }

    /**
     * Loads the LoggerContext using the ContextSelector.
     * @param fqcn The fully qualified class name of the caller.
     * @param loader The ClassLoader to use or null.
     * @param externalContext An external context (such as a ServletContext) to be associated with the LoggerContext.
     * @param currentContext If true returns the current Context, if false returns the Context appropriate
     * for the caller if a more appropriate Context can be determined.
     * @param configLocation The location of the configuration for the LoggerContext.
     * @return The LoggerContext.
     */
    @Override
    public LoggerContext getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
                                    final boolean currentContext, final URI configLocation, final String name) {
        final LoggerContext ctx = selector.getContext(fqcn, loader, currentContext, configLocation);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            if (configLocation != null || name != null) {
                ContextAnchor.THREAD_CONTEXT.set(ctx);
                final Configuration config = ConfigurationFactory.getInstance().getConfiguration(name, configLocation);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration at {}", ctx.getName(), configLocation);
                ctx.start(config);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                ctx.start();
            }
        }
        return ctx;
    }

    /**
     * Returns the ContextSelector.
     * @return The ContextSelector.
     */
    public ContextSelector getSelector() {
        return selector;
    }

    /**
     * Removes knowledge of a LoggerContext.
     *
     * @param context The context to remove.
     */
    @Override
    public void removeContext(final org.apache.logging.log4j.spi.LoggerContext context) {
        if (context instanceof LoggerContext) {
            selector.removeContext((LoggerContext) context);
        }
    }
}
