# Spark2CoreStandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
2. Choose Java 8: `sdk use java 8.0.302-open`
3. Run the application
    1. Local mode: run class `core.localmode.LocalModeApp`
    2. Client mode
        1. Build JAR: `sbt clean package`
        2. Client mode (from IDE): run class `core.clientmode.ClientModeIdeApp`
        3. Client mode (with `spark-submit`):
       ```
       spark-submit \
            --class core.clientmode.ClientModeSparkSubmitApp \
            --master spark://spark-standalone-cluster-master:7077 \
            --deploy-mode client \
            --total-executor-cores 2 \
            --executor-cores 1 \
            --executor-memory 512M \
            --conf "spark.eventLog.enabled=true" \
            --conf "spark.eventLog.dir=file:///media/aleks/ADATA/dataset/spark-events" \
            target/scala-2.12/spark2corestandalone_2.12-1.jar
       ```
    4. Cluster mode (with `spark-submit`)
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
             --class core.clustermode.ClusterModeApp \
             --master spark://spark-standalone-cluster-master:7077 \
             --deploy-mode cluster \
             --total-executor-cores 2 \
             --executor-memory 512M \
             --num-executors 2 \
             --conf "spark.eventLog.enabled=true" \
             --conf "spark.eventLog.dir=file:///datasets/spark-events" \
             file:///datasets/spark2corestandalone_2.12-1.jar
        ```
4. Application in the Spark UI: http://spark-standalone-cluster-master:8080
5. Run "Six Examples"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/sixexercises/submit_cluster_mode.sh app.sixexercises.WarmUp1App`
6. Run "IMBD"
    1. Build: `./build_jar.sh`
    2. Run in cluster mode: `./src/main/scala/app/imdb/submit_cluster_mode.sh app.imdb.Exercise1App`
