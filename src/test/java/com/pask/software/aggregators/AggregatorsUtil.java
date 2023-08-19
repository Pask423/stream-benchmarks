package com.pask.software.aggregators;

import com.pask.software.aggregators.statistics.ComplexRunStatistic;
import com.pask.software.aggregators.statistics.GroupingKey;
import com.pask.software.aggregators.statistics.RunStatistic;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.groupingBy;

public class AggregatorsUtil {

    private static final String SIZE_PARAM_KEY = "size";

    static void saveReports(Collection<RunResult> run, String baseReportName) {
        generateModesReports(run, baseReportName);
        generateTotalReport(run, baseReportName);
    }

    private static void generateModesReports(Collection<RunResult> run, String baseReportName) {
        run.stream()
                .collect(groupingBy(e -> e.getParams().getMode()))
                .values()
                .forEach(e -> {
                    StringBuilder builderNested = new StringBuilder();
                    builderNested.append("Label,Input Size,Threads,Mode,Cnt,Score,Score Mean Error,Units");
                    builderNested.append("\n");
                    e
                            .stream()
                            .map(AggregatorsUtil::toStatistics)
                            .sorted(
                                    Comparator
                                            .comparing(RunStatistic::mode)
                                            .thenComparingDouble(RunStatistic::score)
                                            .thenComparingInt(RunStatistic::inputSize)
                            )
                            .map(RunStatistic::toCsvRow)
                            .forEach(f -> builderNested.append(f).append("\n"));
                    String report = clearUpLabelName(builderNested);
                    saveReport(baseReportName, e.get(0).getParams().getMode().name().toLowerCase(), report);
                });
    }

    private static void generateTotalReport(Collection<RunResult> run, String baseReportName) {
        StringBuilder builder = new StringBuilder();
        builder.append("Label,Input Size,Threads,Cnt,AvgTimeScore,AvgTimeMeanError,AvgUnits,ThroughputScore,ThroughputMeanError,ThroughputUnits");
        builder.append("\n");
        run.stream()
                .collect(groupingBy(e -> {
                    String benchmark = e.getParams().getBenchmark();
                    int inputSize = Integer.parseInt(e.getParams().getParam(SIZE_PARAM_KEY));
                    return new GroupingKey(benchmark, inputSize);
                }))
                .values()
                .stream()
                .map(AggregatorsUtil::toSingleRow)
                .sorted(
                        Comparator
                                .comparing(ComplexRunStatistic::avgtScore)
                                .thenComparingInt(ComplexRunStatistic::inputSize)
                )
                .map(ComplexRunStatistic::toCsvRow)
                .forEach(e -> builder.append(e).append("\n"));
        String report = clearUpLabelName(builder);
        saveReport(baseReportName, "total", report);
    }

    private static RunStatistic toStatistics(RunResult result) {
        Result primaryResult = result.getPrimaryResult();
        String benchmark = result.getParams().getBenchmark();
        return new RunStatistic(
                benchmark,
                Integer.parseInt(result.getParams().getParam(SIZE_PARAM_KEY)),
                resolveThreadNumber(benchmark),
                result.getParams().getMode(),
                primaryResult.getSampleCount(),
                primaryResult.getScore(),
                primaryResult.getScoreError(),
                primaryResult.getScoreUnit());
    }

    private static ComplexRunStatistic toSingleRow(List<RunResult> runs) {
        RunResult modeOne = runs.get(0);
        Result modeOneResults = modeOne.getPrimaryResult();
        BenchmarkParams modeOneParams = modeOne.getParams();
        String benchmark = modeOneParams.getBenchmark();
        RunResult modeTwo = runs.get(1);
        if (modeOneParams.getMode().equals(Mode.AverageTime)) {
            return new ComplexRunStatistic(
                    benchmark,
                    Integer.parseInt(modeOneParams.getParam(SIZE_PARAM_KEY)),
                    resolveThreadNumber(benchmark),
                    modeOneResults.getSampleCount(),
                    modeOneResults.getScore(),
                    modeOneResults.getScoreError(),
                    modeOneResults.getScoreUnit(),
                    modeTwo.getPrimaryResult().getScore(),
                    modeTwo.getPrimaryResult().getScoreError(),
                    modeTwo.getPrimaryResult().getScoreUnit()
            );
        } else {
            return new ComplexRunStatistic(
                    benchmark,
                    Integer.parseInt(modeOneParams.getParam(SIZE_PARAM_KEY)),
                    resolveThreadNumber(benchmark),
                    modeOneResults.getSampleCount(),
                    modeTwo.getPrimaryResult().getScore(),
                    modeTwo.getPrimaryResult().getScoreError(),
                    modeTwo.getPrimaryResult().getScoreUnit(),
                    modeOneResults.getScore(),
                    modeOneResults.getScoreError(),
                    modeOneResults.getScoreUnit()
            );
        }
    }

    private static String clearUpLabelName(StringBuilder builder) {
        return builder.toString()
                .replace("com.pask.software.benchmark.", "")
                .replace("forkjoinnative.", "");
    }

    private static String resolveThreadNumber(String benchmark) {
        if (benchmark.contains("_4")) {
            return "4";
        } else if (benchmark.contains("_8")) {
            return "8";
        } else if (benchmark.contains("_16")) {
            return "16";
        } else if (benchmark.contains("_32")) {
            return "32";
        } else if (benchmark.contains("bench_defaultParallelStream") || benchmark.contains("forkjoinnative")) {
            int commonPoolParallelism = Runtime.getRuntime().availableProcessors() - 1;
            return "" + commonPoolParallelism;
        } else {
            return "1";
        }
    }

    static void saveReport(String reportBasePath, String reportName, String reportCsv) {
        String reportDirPath = "";
        String basePath = reportDirPath + reportBasePath;
        Path path = Path.of(basePath, reportName + ".csv");
        try {
            Path reportDir = Path.of(basePath);
            Files.createDirectories(reportDir);
            Files.writeString(path, reportCsv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
