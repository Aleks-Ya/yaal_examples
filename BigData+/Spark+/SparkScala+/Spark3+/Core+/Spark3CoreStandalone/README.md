# spark3corestandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
2. Choose Java 11 for SBT: `sdk use java 11.0.26-zulu`
3. Choose Java 11 in IntelliJ Idea project
4. Prevent `Cannot run program "/home/aleks/.sdkman/candidates/java/11.0.26-zulu/bin/java"`: `unset JAVA_HOME`
5. Run the application
    1. Local mode: run main class `mode.localmode.LocalModeApp`
    2. Client mode
        1. Disable firewall until reboot: `sudo systemctl stop ufw`
        2. Build JAR: `sbt clean package`
        3. Client mode (from IDE): run class `mode.clientmode.ClientModeIdeApp`
        4. Client mode (with `spark-submit`):
           ```shell
           spark-submit \
           --class mode.clientmode.ClientModeSubmitApp \
           --master spark://spark-standalone-cluster-master:7077 \
           --deploy-mode client \
           --conf "spark.eventLog.enabled=true" \
           --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
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
        3. Run: `./run_in_cluster_mode.sh mode.clustermode.ClusterModeApp`
6. Application in the Spark UI: http://spark-standalone-cluster-master:8080
7. Spark History Server: http://spark-standalone-cluster-master:18080
8. Run in "Hadoop3Cluster"
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
                --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
                target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
9. Stop the application
    1. Enable firewall: `sudo ufw enable`
