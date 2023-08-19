package com.pask.software.benchmark.forkjoinnative;

import com.pask.software.benchmark.TestDataGen;
import com.pask.software.benchmarks.ForkJoinDefinition;
import org.openjdk.jmh.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@State(Scope.Benchmark)
public class ForkJoinState {

    @Param({"0"})
    public int size;
    public List<String> input;
    public ForkJoinPool forkJoinPool_4;
    public ForkJoinPool forkJoinPool_8;
    public ForkJoinPool forkJoinPool_16;
    public ForkJoinPool forkJoinPool_32;
    public final LocalDateTime now = LocalDateTime.now();

    @Setup(Level.Trial)
    public void trialUp() {
        input = new TestDataGen(now).generate(size);
        System.out.println(input.size());
    }

    @Setup(Level.Iteration)
    public void up() {
        forkJoinPool_4 = new ForkJoinPool(4);
        forkJoinPool_8 = new ForkJoinPool(8);
        forkJoinPool_16 = new ForkJoinPool(16);
        forkJoinPool_32 = new ForkJoinPool(32);
    }

    @TearDown(Level.Iteration)
    public void down() {
        forkJoinPool_4.shutdown();
        forkJoinPool_8.shutdown();
        forkJoinPool_16.shutdown();
        forkJoinPool_32.shutdown();
    }
}