set -e
sbt clean package
spark-submit \
  --class metadata.JobMetadataApp \
  --master spark://spark-standalone-cluster-master:7077 \
  --deploy-mode client \
  --conf "spark.eventLog.enabled=true" \
  --conf "spark.eventLog.dir=file:/tmp/spark-standalone-cluster-shared/spark-events" \
  target/scala-2.12/spark3standalonescala212_2.12-1.jar