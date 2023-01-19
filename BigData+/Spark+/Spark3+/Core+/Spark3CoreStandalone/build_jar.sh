set -e
sbt clean package
cp -v target/scala-2.12/spark3corestandalone_2.12-1.jar /media/aleks/ADATA/dataset/