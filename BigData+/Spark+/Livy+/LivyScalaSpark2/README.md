# Livy Scala app

Submit a Livy Scala app on a Standalone Spark cluster.

1. Run a Spark cluster with Livy: `BigData+/Spark+/Livy+/LivyDocker+/Spark2StandaloneLivyDocker`.
2. Build
    1. Choose Java 8: `sdk use java 8.0.302-open`
    2. Choose Scala 2.11: `sdk use scala 2.11.12`
    3. Build JAR: `./build_jar.sh`
3. Submit with spark-submit: `./submit_cluster_mode.sh livy.ClusterModeApp`
4. Submit with Livy (curl):
   ```bash
   curl -X POST \
       -H "Content-Type: application/json" \
       -d '{"className": "livy.ClusterModeApp", "file": "file:///shared/livy-scala-spark2.jar", "name": "ClusterModeAppCurl"}' \
       http://spark2-standalone-livy-cluster-livy:8998/batches
   ```
5. Submit with Livy (Java API): run main class `livy.Main`
