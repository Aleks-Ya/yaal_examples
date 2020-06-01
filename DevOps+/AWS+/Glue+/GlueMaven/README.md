# GlueMaven

Run:
```
mvn clean package
export SPARK_HOME=/home/aleks/installed/spark-2.4.3-bin-hadoop2.8-from-aws-glue
mvn exec:java -Dexec.mainClass="glue.KafkaToS3GlueApp" -Dexec.args="--JOB-NAME theJobName"
```