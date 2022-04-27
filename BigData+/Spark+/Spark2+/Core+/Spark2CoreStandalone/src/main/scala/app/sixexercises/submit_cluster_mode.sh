set -e
echo "Submitting class: $1"
unset JAVA_HOME
spark-submit \
     --class $1 \
     --master spark://spark-standalone-cluster-master:7077 \
     --deploy-mode cluster \
     --driver-cores 1 \
     --driver-memory 512M \
     --executor-cores 1 \
     --executor-memory 512M \
     --num-executors 2 \
     --total-executor-cores 2 \
     --conf "spark.sql.autoBroadcastJoinThreshold=-1" \
     --conf "spark.eventLog.enabled=true" \
     --conf "spark.eventLog.dir=file:///datasets/spark-events" \
     file:///datasets/spark2corestandalone_2.12-1.jar
