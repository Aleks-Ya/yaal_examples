set -e
mvn clean package
docker cp target/YarnKerberosJavaApp-1-jar-with-dependencies.jar yarn-hdfs-kerberos-client:/tmp/YarnKerberosJavaApp.jar
docker cp launch.sh yarn-hdfs-kerberos-client:/tmp/launch.sh
docker exec -it yarn-hdfs-kerberos-client /tmp/launch.sh
