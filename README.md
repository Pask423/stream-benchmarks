## [Benchmarking Java Streams](https://softwaremill.com/benchmarking-java-streams/)

### Benchmarks Report

The results of running benchmarks are stored in `.csv` files. To ease the download of reports, there is a separate `.zip` file named `reports.zip` that contains all the `.csv` files with data.

#### Reports Directory Structure

Reports directories are structured on a per-size basis with three special reports for all input sizes:
- `report_classic` - all input sizes for classic stream
- `report_forkjoin` - all input sizes for fork/join stream
- `report_whole` - all input sizes for both classic and fork/join stream

Each report directory from the above contains 3 separate files:
- `averagetime.csv` - results for average time mode benchmarks
- `throughput.csv` - results for throughput mode benchmarks
- `total.csv` - combined results for both modes

#### Report Formats

For the particular reports, I have two formats:
- `averagetime.csv`, `throughput.csv` share one format, called the modes format.
- `total.csv` has a separate format, called the total format.

##### Modes Format

The modes report contains eight columns:
1. **Label** - name of the benchmark
2. **Input Size** - benchmark input size
3. **Threads** - number of threads used in benchmark from set {1, 4, 7, 8, 16, 19, 32}
4. **Mode** - benchmark mode, either average time or throughput
5. **Cnt** - the number of benchmark iterations, should always be equal to 20
6. **Score** - actual results of benchmark
7. **Score Mean Error** - benchmark measurement error
8. **Units** - units of benchmark, either ms/op (for average time) or ops/ms (for throughput)

##### Total Format

The total report contains ten columns:
1. **Label** - name of the benchmark
2. **Input Size** - benchmark input size
3. **Threads** - number of threads used in benchmark from set {1, 4, 7, 8, 16, 19, 32}
4. **Cnt** - the number of benchmark iterations, should always be equal to 20
5. **AvgTimeScore** - actual results of benchmark for average time mode
6. **AvgTimeMeanError** - benchmark measurement error for average time mode
7. **AvgUnits** - units of benchmark for average time mode in ms/op
8. **ThroughputScore** - actual results of benchmark
9. **ThroughputMeanError** - benchmark measurement error for throughput mode
10. **ThroughputUnits** - units of benchmark for throughput mode in ops/ms