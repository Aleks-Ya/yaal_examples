# spark3corestandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
2. Choose Java 11 for SBT: `sdk use java 11.0.20-zulu`
3. Choose Java 11 in IntelliJ Idea project
4. Prevent `Cannot run program "/home/aleks/.sdkman/candidates/java/11.0.20-zulu/bin/java"`: `unset JAVA_HOME`
5. Run the application
    1. Local mode: run main class `mode.localmode.LocalModeApp`
    2. Client mode
        1. Disable firewall: `sudo ufw disable`
        2. Build JAR: `sbt clean package`
        3. Client mode (from IDE): run class `mode.clientmode.ClientModeIdeApp`
        4. Client mode (with `spark-submit`):
           ```shell
           spark-submit \
           --class mode.clientmode.ClientModeSubmitApp \
           --master spark://spark-standalone-cluster-master:7077 \
           --deploy-mode client \
           --conf "spark.eventLog.enabled=true" \
           --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
           target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
    3. Cluster mode (with `spark-submit`)
        1. Build: `./build_jar.sh`
        2. Execute `unset JAVA_HOME` to prevent error:
           ```shell
           ERROR ClientEndpoint: Exception from cluster was: 
           java.io.IOException: Cannot run program "/home/aleks/.sdkman/candidates/java/8.0.302-open/bin/java" 
           (in directory "/opt/spark/work/driver-20220421045358-0000"): error=2, No such file or directory
           ```
        3. Run:
        ```shell
        spark-submit \
             --class mode.clustermode.ClusterModeApp \
             --master spark://spark-standalone-cluster-master:7077 \
             --deploy-mode cluster \
             --total-executor-cores 2 \
             --executor-memory 512M \
             --num-executors 2 \
             --conf "spark.eventLog.enabled=true" \
             --conf "spark.eventLog.dir=file:///datasets/spark-events" \
             file:///datasets/spark3corestandalone_2.12-1.jar
        ```
6. Application in the Spark UI: http://spark-standalone-cluster-master:8080
7. Logging
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
           --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
           target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
        4. Cluster mode:
           ```shell
           spark-submit \
           --class log.executor.LogClientOrClusterModeSubmitApp \
           --master spark://spark-standalone-cluster-master:7077 \
           --deploy-mode cluster \
           --conf "spark.eventLog.enabled=true" \
           --conf "spark.eventLog.dir=file:///shared/spark-events" \
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
        --conf "spark.eventLog.dir=file:///shared/spark-events" \
        --conf "spark.driver.extraJavaOptions=-Dlogger.level.log.udf=TRACE" \
        --conf "spark.executor.extraJavaOptions=-Dlogger.level.log.udf=TRACE" \
        file:///shared/spark3corestandalone.jar
        ```
8. Run "Six Examples"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/sixexercises/submit_cluster_mode.sh app.sixexercises.WarmUp1App`
9. Run "IMDB"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/imdb/submit_cluster_mode.sh app.imdb.Exercise1App`
10. Run in "Hadoop3Cluster"
    1. Run Hadoop cluster `BigData+/Hadoop+/HadoopDocker+/Hadoop3+/Hadoop3Cluster`
    2. Copy Hadoop configs to host: see `BigData+/Hadoop+/HadoopDocker+/Hadoop3+/Hadoop3Cluster/README.md`
    3. Set env variable `HADOOP_CONF_DIR`=`/tmp/hadoop3-cluster-configs`
        1. Client mode
            1. Build JAR: `sbt clean package`
            2. Client mode (from IDE): run class `mode.clientmode.ClientModeIdeApp`
            3. Client mode (with `spark-submit`):
           ```shell
           sudo su
           export HADOOP_CONF_DIR=/tmp/hadoop3-cluster-configs
           spark-submit \
                --class mode.clientmode.ClientModeSubmitApp \
                --master yarn \
                --deploy-mode client \
                --total-executor-cores 2 \
                --executor-cores 1 \
                --executor-memory 512M \
                --conf "spark.eventLog.enabled=true" \
                --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
                target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
11. Stop the application
    1. Enable firewall: `sudo ufw enable`
