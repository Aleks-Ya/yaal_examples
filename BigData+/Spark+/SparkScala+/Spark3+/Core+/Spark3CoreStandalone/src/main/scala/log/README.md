# Logging in driver or executor

## Run

1. Log on executor
    1. Local mode (from IDE): run main class `log.executor.LogLocalModeApp`
    2. Client mode (from IDE):
        1. Build: `./build_jar.sh`
        2. Run main class `log.executor.LogClientModeIdeApp`
    3. Client mode (from Spark Submit):
       ```shell
       spark-submit \
       --class log.executor.LogClientOrClusterModeSubmitApp \
       --master spark://spark-standalone-cluster-master:7077 \
       --deploy-mode client \
       --conf "spark.eventLog.enabled=true" \
       --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
       target/scala-2.12/spark3corestandalone_2.12-1.jar
       ```
    4. Cluster mode:
       ```shell
       spark-submit \
       --class log.executor.LogClientOrClusterModeSubmitApp \
       --master spark://spark-standalone-cluster-master:7077 \
       --deploy-mode cluster \
       --conf "spark.eventLog.enabled=true" \
       --conf "spark.eventLog.dir=file:/shared/spark-events" \
       --conf "spark.driver.extraJavaOptions=-Dlogger.level.log.executor=TRACE" \
       --conf "spark.executor.extraJavaOptions=-Dlogger.level.log.executor=TRACE" \
       file:///shared/spark3corestandalone.jar
       ```
2. Log from UDF
    1. Local mode (from IDE): run main class `log.udf.UdfLogLocalModeApp`
    2. Cluster mode:
     ```shell
     spark-submit \
     --class log.udf.UdfLogClientOrClusterModeSubmitApp \
     --master spark://spark-standalone-cluster-master:7077 \
     --deploy-mode cluster \
     --conf "spark.eventLog.enabled=true" \
     --conf "spark.eventLog.dir=file:/shared/spark-events" \
     --conf "spark.driver.extraJavaOptions=-Dlogger.level.log.udf=TRACE" \
     --conf "spark.executor.extraJavaOptions=-Dlogger.level.log.udf=TRACE" \
     file:///shared/spark3corestandalone.jar
     ```