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
package org.apache.logging.log4j.core;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.junit.InitialLoggerContext;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.junit.Assert.*;

/**
 *
 */
public class LogEventFactoryTest {

    private static final String CONFIG = "log4j2-config.xml";
    private static final InitialLoggerContext context = new InitialLoggerContext(CONFIG);

    private ListAppender app;

    // this would look so cool using lambdas
    @ClassRule
    public static RuleChain chain = RuleChain.outerRule(new TestRule() {
        @Override
        public Statement apply(final Statement base, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    System.setProperty(Constants.LOG4J_LOG_EVENT_FACTORY, TestLogEventFactory.class.getName());
                    try {
                        base.evaluate();
                    } finally {
                        System.clearProperty(Constants.LOG4J_LOG_EVENT_FACTORY);
                    }
                }
            };
        }
    }).around(context);

    @Before
    public void before() {
        app = context.getListAppender("List").clear();
    }

    @Test
    public void testEvent() {
        final org.apache.logging.log4j.Logger logger = context.getLogger("org.apache.test.LogEventFactory");
        logger.error("error message");
        final List<LogEvent> events = app.getEvents();
        assertNotNull("No events", events);
        assertEquals("Incorrect number of events. Expected 1, actual " + events.size(), 1, events.size());
        final LogEvent event = events.get(0);
        assertEquals("Test LogEventFactory wasn't used", "Test", event.getLoggerName());
    }

    public static class TestLogEventFactory implements LogEventFactory {

        @Override
        public LogEvent createEvent(final String loggerName, final Marker marker,
                                    final String fqcn, final Level level, final Message data,
                                    final List<Property> properties, final Throwable t) {
            return new Log4jLogEvent("Test", marker, fqcn, level, data, properties, t);
        }
    }
}

