# Driver Log Level
Set logging level for Driver.

1. Build JAR: `sbt clean package`
2. Submit:
    1. Local mode: run `log.driver.DriverLogLevelLocalModeApp`
    2. Client mode:
    ```
    spark-submit \
        --class log.driver.DriverLogLevelClientModeApp \
        --master spark://spark-standalone-cluster-master:7077 \
        --deploy-mode client \
        target/scala-2.12/spark3corestandalone_2.12-1.jar
    ```
3. Application in the Spark UI: http://spark-standalone-cluster-master:8080