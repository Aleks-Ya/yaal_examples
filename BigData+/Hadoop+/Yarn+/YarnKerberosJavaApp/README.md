# Java Yarn application with Kerberos

Run:

1. Run Yarn cluster: `Building+/Docker+/DockerImage+/Application+/Hadoop+/Hadoop2+/YarnHdfsKerberosCluster`
1. Build jar file: `mvn clean package`
1. Copy jar file to container:
   `docker cp target/YarnApplication-1-jar-with-dependencies.jar yarn-client:/tmp/YarnApplication.jar`
1. Copy launch script to container: `docker cp launch.sh yarn-client:/tmp/launch.sh`
1. Run app: `docker exec -it yarn-client /tmp/launch.sh`