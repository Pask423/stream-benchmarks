package com.pask.software.benchmark.forkjoinnative;

import com.pask.software.benchmark.BaseBenchmarkConfig;
import com.pask.software.benchmarks.ForkJoinDefinition;
import com.pask.software.model.Ip;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Map;

public class ForkJoinBenchmark extends BaseBenchmarkConfig {

    @Benchmark
    public void bench(ForkJoinState state, Blackhole bh) {
        Map<Ip, Integer> map = new ForkJoinDefinition(state.input, state.now).compute();
        bh.consume(map);
    }

    @Benchmark
    public void bench_customForkJoinPool_4(ForkJoinState state, Blackhole bh) {
        ForkJoinDefinition forkJoinDefinition = new ForkJoinDefinition(state.input, state.now);
        Map<Ip, Integer> map = state.forkJoinPool_4.invoke(forkJoinDefinition);
        bh.consume(map);
    }

    @Benchmark
    public void bench_customForkJoinPool_8(ForkJoinState state, Blackhole bh) {
        ForkJoinDefinition forkJoinDefinition = new ForkJoinDefinition(state.input, state.now);
        Map<Ip, Integer> map = state.forkJoinPool_8.invoke(forkJoinDefinition);
        bh.consume(map);
    }

    @Benchmark
    public void bench_customForkJoinPool_16(ForkJoinState state, Blackhole bh) {
        ForkJoinDefinition forkJoinDefinition = new ForkJoinDefinition(state.input, state.now);
        Map<Ip, Integer> map = state.forkJoinPool_16.invoke(forkJoinDefinition);
        bh.consume(map);
    }

    @Benchmark
    public void bench_customForkJoinPool_32(ForkJoinState state, Blackhole bh) {
        ForkJoinDefinition forkJoinDefinition = new ForkJoinDefinition(state.input, state.now);
        Map<Ip, Integer> map = state.forkJoinPool_32.invoke(forkJoinDefinition);
        bh.consume(map);
    }
}