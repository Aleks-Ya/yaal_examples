# Java Yarn application

Run:

1. Run Yarn cluster: `Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop2+/YarnHdfsCluster`
1. Build jar file: `mvn clean package`
1. Copy jar file to container:
   `docker cp target/YarnApplication-1-jar-with-dependencies.jar yarn-hdfs-client:/tmp/YarnApplication.jar`
1. Copy launch script to container: `docker cp launch.sh yarn-hdfs-client:/tmp/launch.sh`
1. Run app: `docker exec -it yarn-hdfs-client /tmp/launch.sh`