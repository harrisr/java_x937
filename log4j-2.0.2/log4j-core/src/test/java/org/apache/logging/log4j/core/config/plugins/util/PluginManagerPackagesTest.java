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
package org.apache.logging.log4j.core.config.plugins.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PluginManagerPackagesTest {
    private static Configuration config;
    private static ListAppender listAppender;
    private static LoggerContext ctx;

    @BeforeClass
    public static void setupClass() {
    }

    @AfterClass
    public static void cleanupClass() {
        System.clearProperty(ConfigurationFactory.CONFIGURATION_FILE_PROPERTY);
        ctx.reconfigure();
        StatusLogger.getLogger().reset();
    }

    @Test
    public void test() throws Exception {

        // To ensure our custom plugin is NOT included in the log4j plugin metadata file,
        // we make sure the class does not exist until after the build is finished.
        // So we don't create the custom plugin class until this test is run.
        File orig = new File("target/test-classes/customplugin/FixedStringLayout.java.source");
        File f = new File(orig.getParentFile(), "FixedStringLayout.java");
        assertTrue("renamed source file OK", orig.renameTo(f));
        compile(f);
        assertTrue("reverted source file OK", f.renameTo(orig));

        // load the compiled class
        Class.forName("customplugin.FixedStringLayout");

        // now that the custom plugin class exists, we load the config
        // with the packages element pointing to our custom plugin
        ctx = Configurator.initialize("Test1", "customplugin/log4j2-741.xml");
        config = ctx.getConfiguration();
        listAppender = (ListAppender) config.getAppender("List");

        Logger logger = LogManager.getLogger(PluginManagerPackagesTest.class);
        logger.info("this message is ignored");

        List<String> messages = listAppender.getMessages();
        assertEquals(messages.toString(), 1, messages.size());
        assertEquals("abc123XYZ", messages.get(0));
    }

    private void compile(File f) throws IOException {
        // set up compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(f));

        // compile generated source
        // (switch off annotation processing: no need to create Log4j2Plugins.dat)
        List<String> options = Arrays.asList("-proc:none");
        compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();

        // check we don't have any compilation errors
        List<String> errors = new ArrayList<String>();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                errors.add(String.format("Compile error: %s%n", diagnostic.getMessage(Locale.getDefault())));
            }
        }
        fileManager.close();
        assertTrue(errors.toString(), errors.isEmpty());
    }
}
