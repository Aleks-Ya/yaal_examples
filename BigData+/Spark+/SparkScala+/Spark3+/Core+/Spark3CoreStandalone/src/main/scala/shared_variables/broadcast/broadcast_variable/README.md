# Use Broadcast variable on executors

## Run

1. Local mode (from IDE): run main class `shared_variables.broadcast.broadcast_variable.BroadcastVariableLocalModeApp`
2. Client mode (from IDE):
    1. Build: `./build_jar.sh`
    2. Run main class `shared_variables.broadcast.broadcast_variable.BroadcastVariableClientModeIdeApp`
3. Client mode (from Spark Submit):
    1. Build: `./build_jar.sh`
    2. ```shell
       spark-submit \
       --class shared_variables.broadcast.broadcast_variable.BroadcastVariableClientOrClusterModeSubmitApp \
       --master spark://spark-standalone-cluster-master:7077 \
       --deploy-mode client \
       --conf "spark.eventLog.enabled=true" \
       --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
       target/scala-2.12/spark3corestandalone_2.12-1.jar
       ```
4. Cluster mode:
    1. Build: `./build_jar.sh`
    2. `unset JAVA_HOME`
    3. ```shell
       spark-submit \
       --class shared_variables.broadcast.broadcast_variable.BroadcastVariableClientOrClusterModeSubmitApp \
       --master spark://spark-standalone-cluster-master:7077 \
       --deploy-mode cluster \
       --num-executors 2 \
       --executor-cores 1 \
       --conf "spark.eventLog.enabled=true" \
       --conf "spark.eventLog.dir=file:/shared/spark-events" \
       file:///shared/spark3corestandalone.jar
       ```