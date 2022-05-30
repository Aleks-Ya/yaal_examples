# Livy Scala app

Submit a Livy Scala app on a Standalone Spark cluster.

1. Build
    1. Choose Java 8: `sdk use java 8.0.302-open`
    2. Choose Scala 2.11: `sdk use scala 2.11.12`
    3. Build JAR: `./build_jar.sh`
2. Submit with spark-submit: `./submit_cluster_mode.sh livy.ClusterModeApp`
3. Submit with Livy: run main class `livy.Main`
