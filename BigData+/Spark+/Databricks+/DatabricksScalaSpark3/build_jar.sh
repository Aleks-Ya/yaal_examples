set -e
sbt clean package
mkdir -p /tmp/spark-standalone-cluster-shared
cp -v target/scala-2.12/databricksscalaspark3_2.12-1.jar /tmp/spark-standalone-cluster-shared/databricks-scala-spark3.jar
