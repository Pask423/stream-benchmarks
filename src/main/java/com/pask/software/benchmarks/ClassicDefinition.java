package com.pask.software.benchmarks;

import com.pask.software.model.Ip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static com.pask.software.Logic.groupByIncomingIp;
import static com.pask.software.Constants.TIME_WINDOW;

public class ClassicDefinition {

    private final LocalDateTime lowerTimeBound;
    private final LocalDateTime upperTimeBound;

    public ClassicDefinition(LocalDateTime now) {
        this.lowerTimeBound = now.minusSeconds(TIME_WINDOW);
        this.upperTimeBound = now.plusSeconds(TIME_WINDOW);
    }

    public Map<Ip, Integer> sequentialStream(List<String> requests) {
        return groupByIncomingIp(requests.stream(), upperTimeBound, lowerTimeBound);
    }

    public Map<Ip, Integer> defaultParallelStream(List<String> requests) {
        return groupByIncomingIp(requests.parallelStream(), upperTimeBound, lowerTimeBound);
    }

    public Map<Ip, Integer> parallelStreamWithCustomForkJoinPool(ForkJoinPool forkJoinPool, List<String> requests) {
        try {
            return forkJoinPool
                    .submit(() -> groupByIncomingIp(requests.parallelStream(), upperTimeBound, lowerTimeBound))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}