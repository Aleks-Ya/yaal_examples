# Spark2CoreStandalone

Submit:
1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/SparkStandaloneDocker`
2. Choose Java 8: `sdk use java 8.0.302-open`
3. Build JAR: `sbt package`
4. Submit Spark app: 
```
spark-submit \
    --class core.HelloWorldTest \
    --master spark://spark-standalone-cluster-master:7077 \
    --deploy-mode client \
    target/scala-2.11/spark2corestandalone_2.11-1.jar
```
5. Application in the Spark UI: http://spark-standalone-cluster-master:8080