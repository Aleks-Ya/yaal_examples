# Maven on Docker

DockerHub: https://hub.docker.com/_/maven

Build: `docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean package`

docker run -it --rm -v "$PWD":/usr/src/mymaven -v "$HOME/.m2":/root/.m2 -v "$PWD/target:/usr/src/mymaven/target" -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean package  


```shell
docker run -it --rm \
	-v "$PWD":/usr/src/mymaven \
	-v "$HOME/.m2":/root/.m2 \
	-w /usr/src/mymaven \
	maven:3.3-jdk-8 \
	mvn clean package
```  
