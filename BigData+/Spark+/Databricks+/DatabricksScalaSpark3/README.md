# Databricks Scala app

Submit a Scala app on a Standalone Spark cluster.

1. Run a Spark cluster `BigData+/Spark+/SparkDocker+/Spark3StandaloneDocker`.
2. Build
    1. Choose Java 1: `sdk use java 11.0.12-open`
    2. Choose Scala 2.12: `sdk use scala 2.12.17`
    3. Build JAR: `./build_jar.sh`
3. Submit to a local cluster with spark-submit:
   `./submit_cluster_mode.sh databricks.cluster.shorttrem.ShortTermDatabricksClusterModeApp`
4. Run in Databricks
    1. Short-term job
        1. Type: `JAR`
        2. Main class: `databricks.cluster.shorttrem.ShortTermDatabricksClusterModeApp`
        3. JAR: `target/scala-2.12/databricksscalaspark3_2.12-1.jar`
        4. Parameters: `["Hello", "World"]`
    2. Long-term job
        1. Type: `JAR`
        2. Main class: `databricks.cluster.longterm.LongTermDatabricksClusterModeApp`
        3. JAR: `target/scala-2.12/databricksscalaspark3_2.12-1.jar`
        4. Parameters: `["60","30","120","180","35","45"]`
