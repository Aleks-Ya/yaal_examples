docker run -d --network host --name nexus sonatype/nexus
docker build --network host -t maven-to-docker .
docker run -it maven-to-docker