# spark3corestandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
2. Choose Java 8 for SBT: `sdk use java 11.0.12-open`
3. Choose Java 8 in IntelliJ Idea project
4. Run the application
    1. Local mode: run main class `mode.localmode.LocalModeApp`
    2. Client mode
        1. Turn off the firewall: `sudo ufw disable` 
        2. Build JAR: `sbt clean package`
        3. Client mode (from IDE): run class `mode.clientmode.ClientModeIdeApp`
        4. Client mode (with `spark-submit`):
           ```
           spark-submit \
           --class mode.clientmode.ClientModeSparkSubmitApp \
           --master spark://spark-standalone-cluster-master:7077 \
           --deploy-mode client \
           --conf "spark.eventLog.enabled=true" \
           --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
           target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
    3. Cluster mode (with `spark-submit`)
        1. Build: `./build_jar.sh`
        2. Execute `unset JAVA_HOME` to prevent error:
           ```
           ERROR ClientEndpoint: Exception from cluster was: 
           java.io.IOException: Cannot run program "/home/aleks/.sdkman/candidates/java/8.0.302-open/bin/java" 
           (in directory "/opt/spark/work/driver-20220421045358-0000"): error=2, No such file or directory
           ```
        3. Run:
        ```
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
5. Application in the Spark UI: http://spark-standalone-cluster-master:8080
6. Logging
   1. Log on executor
      1. Local mode (from IDE): run main class `log.executor.LogLocalModeApp`
7. Run "Six Examples"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/sixexercises/submit_cluster_mode.sh app.sixexercises.WarmUp1App`
8. Run "IMDB"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/imdb/submit_cluster_mode.sh app.imdb.Exercise1App`
9. Run in "Hadoop3Cluster"
    1. Run Hadoop cluster `BigData+/Hadoop+/HadoopDocker+/Hadoop3+/Hadoop3Cluster`
    2. Copy Hadoop configs to host: see `BigData+/Hadoop+/HadoopDocker+/Hadoop3+/Hadoop3Cluster/README.md`
    3. Set env variable `HADOOP_CONF_DIR`=`/tmp/hadoop3-cluster-configs`
        1. Client mode
            1. Build JAR: `sbt clean package`
            2. Client mode (from IDE): run class `mode.clientmode.ClientModeIdeApp`
            3. Client mode (with `spark-submit`):
           ```
           sudo su
           export HADOOP_CONF_DIR=/tmp/hadoop3-cluster-configs
           spark-submit \
                --class mode.clientmode.ClientModeSparkSubmitApp \
                --master yarn \
                --deploy-mode client \
                --total-executor-cores 2 \
                --executor-cores 1 \
                --executor-memory 512M \
                --conf "spark.eventLog.enabled=true" \
                --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
                target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
