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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.junit.InitialLoggerContext;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.message.TimestampMessage;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.apache.logging.log4j.util.Strings;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Confirms that if you log a {@link TimestampMessage} then there are no unnecessary calls to {@link Clock}.
 * <p/>
 * See LOG4J2-744.
 */
public class TimestampMessageTest {
    private ListAppender app;

    @ClassRule
    public static InitialLoggerContext context = new InitialLoggerContext("log4j2-744.xml");

    @BeforeClass
    public static void beforeClass() {
        System.setProperty(ClockFactory.PROPERTY_NAME, PoisonClock.class.getName());
    }

    @Before
    public void setUp() throws Exception {
        this.app = context.getListAppender("List").clear();
    }

    @AfterClass
    public static void afterClass() {
        System.setProperty(Constants.LOG4J_CONTEXT_SELECTOR, Strings.EMPTY);
        System.setProperty(ClockFactory.PROPERTY_NAME, Strings.EMPTY);
    }

    @Test
    public void testTimestampMessage() {
        final Logger log = context.getLogger("TimestampMessageTest");
        log.info(new TimeMsg("Message with embedded timestamp", 123456789000L));
        final List<String> msgs = app.getMessages();
        assertNotNull(msgs);
        assertEquals(1, msgs.size());
        final String NL = System.getProperty("line.separator");
        assertEquals("123456789000 Message with embedded timestamp" + NL, msgs.get(0));
    }

    public static class PoisonClock implements Clock {
        @Override
        public long currentTimeMillis() {
            throw new RuntimeException("This should not have been called");
        }
    }

    static class TimeMsg extends SimpleMessage implements TimestampMessage {
        private static final long serialVersionUID = 1L;
        private final long timestamp;

        public TimeMsg(final String msg, final long timestamp) {
            super(msg);
            this.timestamp = timestamp;
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }
    }
}
