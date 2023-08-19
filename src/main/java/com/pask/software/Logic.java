package com.pask.software;

import com.pask.software.model.Ip;
import com.pask.software.model.Request;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class Logic {

    private Logic() {
    }

    public static Map<Ip, Integer> groupByIncomingIp(Stream<String> requests, LocalDateTime upperTimeBound, LocalDateTime lowerTimeBound) {
        return requests
                .map(line -> line.split(","))
                .filter(words -> words.length == 3)
                .map(words -> new Request(words[1], LocalDateTime.parse(words[2])))
                .filter(request -> request.timestamp().isBefore(upperTimeBound) && request.timestamp().isAfter(lowerTimeBound))
                .map(i -> new Ip(i.ip()))
                .collect(groupingBy(i -> i, summingInt(i -> 1)));
    }
}