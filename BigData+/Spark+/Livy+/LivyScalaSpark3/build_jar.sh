set -e
sbt clean package
#cp -v target/scala-2.12/spark2corestandalone_2.12-1.jar /media/aleks/ADATA/dataset/
#cp -v target/scala-2.11/livy-scala-fat.jar /home/aleks/tmp/livy_shared/
mkdir -p /tmp/spark-standalone-cluster-shared
cp -v target/scala-2.12/livyscalaspark3_2.12-1.jar /home/aleks/tmp/livy_shared/livy-scala.jar
