#!/usr/bin/env bash

export SPARK_CONF_DIR=/usr/local/airflow/hadoop-conf/

spark-submit \
    --name "spark-3-scala-maven" \
    --class $MAIN_CLASS \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 512m \
    --executor-memory 512m \
    --executor-cores 1 \
    --verbose \
    --queue default \
    --num-executors 1 \
    --conf "spark.hadoop.yarn.timeline-service.enabled=false" \
    --conf "spark.driver.extraJavaOptions=-Dfile.encoding=UTF-8 -Dhdp.version=2.6.2.0-205 -Duser.timezone=UTC " \
    --conf "spark.executor.extraJavaOptions=-Dfile.encoding=UTF-8 -Dhdp.version=2.6.2.0-205 -Duser.timezone=UTC " \
    --conf "spark.yarn.archive=hdfs://epccluster/hdp-spark-jars/hdp-spark-jars-2.zip" \
    --conf "spark.eventLog.enabled=true" \
    --conf "spark.eventLog.dir=hdfs:///spark2-history/" \
    --conf "spark.yarn.maxAppAttempts=1" \
    /usr/local/airflow/integration/emiss/spark-3-scala-maven-1-jar-with-dependencies.jar
