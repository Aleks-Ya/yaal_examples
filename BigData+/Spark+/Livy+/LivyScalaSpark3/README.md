# Livy Scala app

Submit a Livy Scala app on a Standalone Spark cluster.

1. Run a Spark cluster with Livy: `BigData+/Spark+/Livy+/LivyDocker+/SparkStandaloneLivyDocker`.
2. Build
    1. Choose Java 1: `sdk use java 11.0.12-open`
    2. Choose Scala 2.12: `sdk use scala 2.12.17`
    3. Build JAR: `./build_jar.sh`
3. Submit with spark-submit: `./submit_cluster_mode.sh livy.ClusterModeApp`
4. Submit with Livy: run main class `livy.Main`
