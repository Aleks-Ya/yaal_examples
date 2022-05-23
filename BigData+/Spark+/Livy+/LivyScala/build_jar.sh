set -e
sbt clean package
#cp -v target/scala-2.12/spark2corestandalone_2.12-1.jar /media/aleks/ADATA/dataset/
#cp -v target/scala-2.11/livy-scala-fat.jar /home/aleks/tmp/livy_shared/
cp -v target/scala-2.11/livyscala_2.11-1.jar /home/aleks/tmp/livy_shared/livy-scala.jar