set -e
mvn clean package
docker cp client/target/yarn-kerberos-java-app-client-1-jar-with-dependencies.jar yarn-hdfs-kerberos-client:/tmp/client.jar
docker cp container/target/yarn-kerberos-java-app-container-1-jar-with-dependencies.jar yarn-hdfs-kerberos-client:/tmp/container.jar
docker cp am/target/yarn-kerberos-java-app-am-1-jar-with-dependencies.jar yarn-hdfs-kerberos-client:/tmp/am.jar
docker cp launch.sh yarn-hdfs-kerberos-client:/tmp/launch.sh
docker exec -it yarn-hdfs-kerberos-client /tmp/launch.sh
