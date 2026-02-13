# Use `LongAccumulator` on executors

## Run

1. Local mode (from IDE): run main class `shared_variables.accumulator.long_accumulator.LongAccumulatorLocalModeApp`
2. Client mode (from IDE):
    1. Build: `./build_jar.sh`
    2. Run main class `shared_variables.accumulator.long_accumulator.LongAccumulatorClientModeIdeApp`
3. Client mode (from Spark Submit):
    1. Build: `./build_jar.sh`
    2. ```shell
       spark-submit \
       --class shared_variables.accumulator.long_accumulator.LongAccumulatorClientOrClusterModeSubmitApp \
       --master spark://spark-standalone-cluster-master:7077 \
       --deploy-mode client \
       --conf "spark.eventLog.enabled=true" \
       --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
       target/scala-2.12/spark3corestandalone_2.12-1.jar
       ```
4. Cluster mode:
    1. Build: `./build_jar.sh`
    2. Run:
       `./run_in_cluster_mode.sh shared_variables.accumulator.long_accumulator.LongAccumulatorClientOrClusterModeSubmitApp`