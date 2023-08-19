package com.pask.software.benchmark;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Warmup(iterations = 5, time = 10, timeUnit = SECONDS)
@Measurement(iterations = 20, time = 10, timeUnit = SECONDS)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(MILLISECONDS)
@Fork(1)
@Threads(1)
public class BaseBenchmarkConfig {

}
