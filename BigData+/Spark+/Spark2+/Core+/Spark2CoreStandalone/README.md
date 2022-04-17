# Spark2CoreStandalone

Submit:

1. Run Spark Standalone cluster from `BigData+/Spark+/SparkDocker+/SparkStandaloneDocker`
2. Choose Java 8: `sdk use java 8.0.302-open`
3. Build JAR: `sbt clean package`
4. Run the application
    1. From Idea: run `core.HelloWorldApp`
    2. With spark-submit:
    ```
    spark-submit \
        --class core.HelloWorldApp \
        --master spark://spark-standalone-cluster-master:7077 \
        --deploy-mode client \
        target/scala-2.12/spark2corestandalone_2.12-1.jar
    ```
7. Application in the Spark UI: http://spark-standalone-cluster-master:8080