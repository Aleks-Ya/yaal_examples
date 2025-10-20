# spark3corestandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`
2. Choose Java 11 for SBT: `sdk use java 11.0.28-zulu`
3. Choose Java 11 in the IntelliJ IDEA project
4. Prevent an error: `unset JAVA_HOME`
5. Run the application in Local mode
    1. Run main class `mode.localmode.LocalModeApp`
6. Run the application in Client mode
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
7. Run the application in Cluster mode (with `spark-submit`)
    1. Build: `./build_jar.sh`
    2. Run: `./run_in_cluster_mode.sh mode.clustermode.ClusterModeApp`
    3. Download dependencies by `spark-submit --packages`
        1. From Maven Central:
            ```shell
            ./run_in_cluster_mode.sh dependencies.ClusterModeApp \
            --packages com.vladsch.flexmark:flexmark-all:0.64.8
            ```
        2. From Artifactory:
            1. Publish an artifact to Artifactory: `Building+/Ant+/Ivy+/PublishToArtifactoryMaven`
            2. Copy: `cp ivysettings.xml /tmp/spark-standalone-cluster-shared`
            3. Run:
                 ```shell
                 ./run_in_cluster_mode.sh dependencies.artifactory.ClusterModeApp \
                 --packages ru.yaal.examples.building.maven:DeployToArtifactory:1 \
                 --conf "spark.jars.repositories=http://172.17.0.1:8082/artifactory/libs-release-local/" \
                 --conf "spark.jars.ivySettings=/shared/ivysettings.xml"
                 ```
8. Application in the Spark UI: http://spark-standalone-cluster-master:8080
9. Spark History Server: http://spark-standalone-cluster-master:18080
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
                --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
                target/scala-2.12/spark3corestandalone_2.12-1.jar
           ```
11. Stop the application
    1. Enable firewall: `sudo ufw enable`

## Errors
### No such file or directory
Command: `./build_jar.sh`
Message:
```shell
ERROR ClientEndpoint: Exception from cluster was: 
java.io.IOException: Cannot run program "/home/aleks/.sdkman/candidates/java/8.0.302-open/bin/java" 
(in directory "/opt/spark/work/driver-20220421045358-0000"): error=2, No such file or directory
```
Solution: `unset JAVA_HOME`
