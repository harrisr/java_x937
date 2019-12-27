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

package org.apache.logging.log4j.perf.jmh;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 * Tests Log4j2 Formatter Logger performance.
 */
// ============================== HOW TO RUN THIS TEST: ====================================
//
// single thread:
// java -jar log4j-perf/target/microbenchmarks.jar ".*FormatterLogger.*" -f 1 -wi 5 -i 5
//
// Usage help:
// java -jar log4j-perf/target/microbenchmarks.jar -help
//
@State(Scope.Thread)
public class FormatterLoggerBenchmark {

    Logger logger;
    Logger formatterLogger;

    @Setup(Level.Trial)
    public void up() {
        new File("perftest.log").delete();
        System.setProperty("log4j.configurationFile", "perf3PlainNoLoc.xml");
        logger = LogManager.getLogger(getClass());
        formatterLogger = LogManager.getLogger("formatter");
    }

    @TearDown(Level.Trial)
    public void down() {
        ((LifeCycle) LogManager.getContext(false)).stop();
        new File("perftest.log").delete();
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public boolean throughputBaseline() {
        return logger.isInfoEnabled();
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void throughputLoggerString() {
        logger.info("Message with {} parameter", "string");
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void throughputLoggerDouble() {
        logger.info("Message with double param: {}", Math.PI);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void throughputFormatterLoggerString() {
        formatterLogger.info("Message with %s parameter", "string");
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void throughputFormatterLoggerDouble() {
        formatterLogger.info("Message with double param: %f", Math.PI);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean latencyBaseline() {
        return logger.isInfoEnabled();
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void latencyLoggerString() {
        logger.info("Message with {} parameter", "string");
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void latencyLoggerDouble() {
        logger.info("Message with double param: {}", Math.PI);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void latencyFormatterLoggerString() {
        formatterLogger.info("Message with %s parameter", "string");
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void latencyFormatterLoggerDouble() {
        formatterLogger.info("Message with double param: %f", Math.PI);
    }
}
