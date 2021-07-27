spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 1g \
    --executor-memory 1g \
    --executor-cores 1 \
    --queue default \
    "${SPARK_HOME}"/examples/jars/spark-examples*.jar \
    10
