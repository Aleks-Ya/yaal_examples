set -e
mvn clean package
docker cp client/target/yarn-java-app-client-1-jar-with-dependencies.jar yarn-hdfs-client:/tmp/client.jar
docker cp container/target/yarn-java-app-container-1-jar-with-dependencies.jar yarn-hdfs-client:/tmp/container.jar
docker cp am/target/yarn-java-app-am-1-jar-with-dependencies.jar yarn-hdfs-client:/tmp/am.jar
docker cp launch.sh yarn-hdfs-client:/tmp/launch.sh
docker exec -it yarn-hdfs-client /tmp/launch.sh
