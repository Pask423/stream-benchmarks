package com.pask.software.benchmarks;

import com.pask.software.model.Ip;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.pask.software.Logic.groupByIncomingIp;
import static com.pask.software.Constants.TIME_WINDOW;

public class ForkJoinDefinition extends RecursiveTask<Map<Ip, Integer>> {

    private static final int THRESHOLD = 20;

    private final List<String> data;
    private final LocalDateTime now;
    private final LocalDateTime lowerTimeBound;
    private final LocalDateTime upperTimeBound;

    public ForkJoinDefinition(List<String> data, LocalDateTime now) {
        this.data = data;
        this.now = now;
        this.lowerTimeBound = now.minusSeconds(TIME_WINDOW);
        this.upperTimeBound = now.plusSeconds(TIME_WINDOW);
    }

    @Override
    public Map<Ip, Integer> compute() {
        if (data.size() >= THRESHOLD) {
            Map<Ip, Integer> output = new HashMap<>();
            ForkJoinTask
                    .invokeAll(createSubTasks())
                    .forEach(task -> task
                            .join()
                            .forEach((k, v) -> updateOutput(k, v, output))
                    );
            return output;
        }
        return process();
    }

    private void updateOutput(Ip k, Integer v, Map<Ip, Integer> output) {
        Integer currentValue = output.get(k);
        if (currentValue == null) {
            output.put(k, v);
        } else {
            output.replace(k, currentValue + v);
        }
    }

    private List<ForkJoinDefinition> createSubTasks() {
        int size = data.size();
        int middle = size / 2;
        return List.of(
                new ForkJoinDefinition(new ArrayList<>(data.subList(0, middle)), now),
                new ForkJoinDefinition(new ArrayList<>(data.subList(middle, size)), now)
        );
    }

    private Map<Ip, Integer> process() {
        return groupByIncomingIp(data.stream(), upperTimeBound, lowerTimeBound);
    }
}