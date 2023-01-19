set -e
echo "Submitting class: '$1'"
if [ -z "$1" ]
then
  echo "Error: submitting class is missed"
  exit 1
fi
unset JAVA_HOME
/home/aleks/installed/spark-2.4.8-bin-hadoop2.7/bin/spark-submit \
     --class $1 \
     --master spark://spark2-standalone-livy-cluster-master:7077 \
     --deploy-mode cluster \
     --driver-cores 1 \
     --driver-memory 512M \
     --executor-cores 1 \
     --executor-memory 512M \
     --num-executors 2 \
     --total-executor-cores 2 \
     --conf "spark.sql.autoBroadcastJoinThreshold=-1" \
     --conf "spark.eventLog.enabled=true" \
     --conf "spark.eventLog.dir=file:///shared/spark-events" \
     file:///shared/livy-scala-spark2.jar \
     Hello World !
