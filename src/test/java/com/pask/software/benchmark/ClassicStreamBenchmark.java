package com.pask.software.benchmark;

import com.pask.software.model.Ip;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Map;

public class ClassicStreamBenchmark extends BaseBenchmarkConfig {

    @Benchmark
    public void bench_sequential(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.sequentialStream(state.input);
        bh.consume(map);
    }

    @Benchmark
    public void bench_defaultParallelStream(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.defaultParallelStream(state.input);
        bh.consume(map);
    }

    @Benchmark
    public void bench_parallelStreamWithCustomForkJoinPool_4(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.parallelStreamWithCustomForkJoinPool(state.forkJoinPool_4, state.input);
        bh.consume(map);
    }

    @Benchmark
    public void bench_parallelStreamWithCustomForkJoinPool_8(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.parallelStreamWithCustomForkJoinPool(state.forkJoinPool_8, state.input);
        bh.consume(map);
    }

    @Benchmark
    public void bench_parallelStreamWithCustomForkJoinPool_16(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.parallelStreamWithCustomForkJoinPool(state.forkJoinPool_16, state.input);
        bh.consume(map);
    }

    @Benchmark
    public void bench_parallelStreamWithCustomForkJoinPool_32(BenchmarkState state, Blackhole bh) {
        Map<Ip, Integer> map = state.definitions.parallelStreamWithCustomForkJoinPool(state.forkJoinPool_32, state.input);
        bh.consume(map);
    }
}