# Jenkins v2
## Run Docker container
```
docker build -t aleks3490/jenkins:2.60.3 .
docker rm jenkins_2.60.3
docker run -p 8080:8080 -p 50000:50000 --net bridge --name jenkins_2.60.3 aleks3490/jenkins:2.60.3
```
## Attach with bash
`docker exec -it jenkins16 bash`

## List installed plugins in container
`docker exec jenkins16 ls -l /usr/share/jenkins/ref/plugins`

## Open Jenkins in browser:
http://localhost:8080

## Run dev server for A app:
`docker run -p 52022:22 -tid --name a-dev docker-ssh-connection:1`

## Jenkins CLI
### Connect via Jenkins CLI:
```
export JENKINS_URL="http://localhost:8080"

wget "$JENKINS_URL/jnlpJars/jenkins-cli.jar"

java -jar jenkins-cli.jar -s $JENKINS_URL help
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs
java -jar jenkins-cli.jar -s $JENKINS_URL safe-restart
java -jar jenkins-cli.jar -s $JENKINS_URL restart
```

#### Login and password authorization
```
# Single command
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs --username aleks --password pass

# Many commands
java -jar jenkins-cli.jar -s $JENKINS_URL login --username aleks --password pass
java -jar jenkins-cli.jar -s $JENKINS_URL who-am-i
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs
java -jar jenkins-cli.jar -s $JENKINS_URL logout
```

### See CLI commands
http://localhost:8080/cli
***
