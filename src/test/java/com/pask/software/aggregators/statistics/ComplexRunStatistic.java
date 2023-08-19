package com.pask.software.aggregators.statistics;

public record ComplexRunStatistic(
        String label,
        int inputSize,
        String threadNumber,
        long cnt,
        double avgtScore,
        double avgtMeanError,
        String avgtUnit,
        double thrptScore,
        double thrptMeanError ,
        String thrptUnit
) {

    public String toCsvRow() {
        return label + "," +
                inputSize + "," +
                threadNumber + "," +
                cnt + "," +
                avgtScore + "," +
                avgtMeanError + "," +
                avgtUnit + "," +
                thrptScore + "," +
                thrptMeanError + "," +
                thrptUnit;
    }
}