package com.pask.software.benchmark;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.pask.software.Constants.TIME_WINDOW;

public class TestDataGen {

    private static final List<String> IPS =
            List.of("63.40.60.183",
                    "11.237.144.23",
                    "249.20.123.122",
                    "117.250.188.218",
                    "24.185.175.159",
                    "128.248.83.81",
                    "165.151.160.149",
                    "36.105.197.15",
                    "97.185.9.124",
                    "202.168.222.1",
                    "49.190.64.142",
                    "222.40.3.6",
                    "91.0.139.90",
                    "144.160.223.131",
                    "196.213.41.194",
                    "123.77.30.91",
                    "37.73.80.51",
                    "197.186.97.129",
                    "182.47.2.6",
                    "1.89.49.20");

    private final LocalDateTime now;

    public TestDataGen(LocalDateTime now) {
        this.now = now;
    }

    public List<String> generate(int length) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return IntStream
                .range(0, length)
                .mapToObj(i -> {
                    LocalDateTime requestTimestamp = requestTimestamp(i, current);
                    return i + "," + IPS.get(i % 20) + "," + requestTimestamp;
                })
                .toList();
    }

    private LocalDateTime requestTimestamp(int i, ThreadLocalRandom current) {
        if (i % 2 == 0) {
            return now.minusSeconds(current.nextLong(TIME_WINDOW));
        } else {
            return now.plusSeconds(current.nextLong(TIME_WINDOW));
        }
    }
}
