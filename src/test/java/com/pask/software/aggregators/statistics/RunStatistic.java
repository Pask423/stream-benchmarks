package com.pask.software.aggregators.statistics;

import org.openjdk.jmh.annotations.Mode;

public record RunStatistic(
        String label,
        int inputSize,
        String threadNumber,
        Mode mode,
        long cnt,
        double score,
        double meanError,
        String units
) {

    public String toCsvRow() {
        return label + ", " +
                inputSize + "," +
                threadNumber + "," +
                mode + "," +
                cnt + "," +
                score + "," +
                meanError + "," +
                units;
    }
}
