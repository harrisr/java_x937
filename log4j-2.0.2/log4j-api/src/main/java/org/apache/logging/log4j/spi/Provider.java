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
package org.apache.logging.log4j.spi;

import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * Model class for a Log4j 2 provider. The properties in this class correspond to the properties used in a
 * {@code META-INF/log4j-provider.properties} file. Note that this class is automatically created by Log4j and should
 * not be used by providers.
 */
public class Provider {
    private static final Integer DEFAULT_PRIORITY = Integer.valueOf(-1);
    /**
     * Property name to set for a Log4j 2 provider to specify the priority of this implementation.
     */
    public static final String FACTORY_PRIORITY = "FactoryPriority";
    /**
     * Property name to set to the implementation of {@link org.apache.logging.log4j.spi.ThreadContextMap}.
     */
    public static final String THREAD_CONTEXT_MAP = "ThreadContextMap";
    /**
     * Property name to set to the implementation of {@link org.apache.logging.log4j.spi.LoggerContextFactory}.
     */
    public static final String LOGGER_CONTEXT_FACTORY = "LoggerContextFactory";

    private static final Logger LOGGER = StatusLogger.getLogger();

    private final Integer priority;
    private final String className;
    private final String threadContextMap;
    private final URL url;
    private final ClassLoader classLoader;

    public Provider(final Properties props, final URL url, final ClassLoader classLoader) {
        this.url = url;
        this.classLoader = classLoader;
        final String weight = props.getProperty(FACTORY_PRIORITY);
        priority = weight == null ? DEFAULT_PRIORITY : Integer.valueOf(weight);
        className = props.getProperty(LOGGER_CONTEXT_FACTORY);
        threadContextMap = props.getProperty(THREAD_CONTEXT_MAP);
    }

    /**
     * Gets the priority (natural ordering) of this Provider.
     *
     * @return the priority of this Provider
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Gets the class name of the {@link org.apache.logging.log4j.spi.LoggerContextFactory} implementation of this
     * Provider.
     *
     * @return the class name of a LoggerContextFactory implementation
     */
    public String getClassName() {
        return className;
    }

    /**
     * Loads the {@link org.apache.logging.log4j.spi.LoggerContextFactory} class specified by this Provider.
     *
     * @return the LoggerContextFactory implementation class or {@code null} if there was an error loading it
     */
    @SuppressWarnings("unchecked")
    public Class<? extends LoggerContextFactory> loadLoggerContextFactory() {
        if (className == null) {
            return null;
        }
        try {
            final Class<?> clazz = classLoader.loadClass(className);
            if (LoggerContextFactory.class.isAssignableFrom(clazz)) {
                return (Class<? extends LoggerContextFactory>) clazz;
            }
        } catch (final Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", className, url.toString(), e);
        }
        return null;
    }

    /**
     * Gets the class name of the {@link org.apache.logging.log4j.spi.ThreadContextMap} implementation of this
     * Provider.
     *
     * @return the class name of a ThreadContextMap implementation
     */
    public String getThreadContextMap() {
        return threadContextMap;
    }

    /**
     * Loads the {@link org.apache.logging.log4j.spi.ThreadContextMap} class specified by this Provider.
     *
     * @return the ThreadContextMap implementation class or {@code null} if there was an error loading it
     */
    @SuppressWarnings("unchecked")
    public Class<? extends ThreadContextMap> loadThreadContextMap() {
        if (threadContextMap == null) {
            return null;
        }
        try {
            final Class<?> clazz = classLoader.loadClass(threadContextMap);
            if (ThreadContextMap.class.isAssignableFrom(clazz)) {
                return (Class<? extends ThreadContextMap>) clazz;
            }
        } catch (final Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", threadContextMap, url.toString(), e);
        }
        return null;
    }

    /**
     * Gets the URL containing this Provider's Log4j details.
     *
     * @return the URL corresponding to the Provider {@code META-INF/log4j-provider.properties} file
     */
    public URL getUrl() {
        return url;
    }
}
