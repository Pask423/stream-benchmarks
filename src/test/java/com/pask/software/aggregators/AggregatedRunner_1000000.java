package com.pask.software.aggregators;

import com.pask.software.benchmark.ClassicStreamBenchmark;
import com.pask.software.benchmark.forkjoinnative.ForkJoinBenchmark;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.Collection;
import static com.pask.software.aggregators.AggregatorsUtil.saveReports;

public class AggregatedRunner_1000000 {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ClassicStreamBenchmark.class.getSimpleName())
                .include(ForkJoinBenchmark.class.getSimpleName())
                .param("size", "1000000")
                .build();

        Collection<RunResult> run = new Runner(opt).run();
        saveReports(run, "report_1000000");
    }
}