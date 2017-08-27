#Jenkins v2:
docker run -p 8080:8080 -p 50000:50000 --net bridge --dns 8.8.8.8  jenkins

#Jenkins v1:
##Run Docker container
docker build -t aleks3490/jenkins:1.651.3 -f Jenkins_v1_Dockerfile .

docker rm jenkins16

docker run -p 8080:8080 -p 50000:50000 --net bridge --dns 8.8.8.8 --dns 10.66.0.6 --name jenkins16 aleks3490/jenkins:1.651.3_v1_plugin_installed

Attach with bash: docker exec -it jenkins16 bash

##Open Jenkins in browser:
http://localhost:8080

##Run dev server for A app:
docker run -p 52022:22 -tid --name a-dev docker-ssh-connection:1

##Jenkins CLI
###Connect via Jenkins CLI:
export JENKINS_URL="http://localhost:8080"

wget "$JENKINS_URL/jnlpJars/jenkins-cli.jar"

java -jar jenkins-cli.jar -s $JENKINS_URL help

###See CLI commands
http://localhost:8080/cli