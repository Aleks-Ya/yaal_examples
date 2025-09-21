# Jenkins v2

Source: https://hub.docker.com/r/jenkins/jenkins

## Run Docker container
1. Build and run: `./build_and_run.sh 2.303.2`
2. Open: http://localhost:8080

## Attach with Bash
`docker exec -it jenkins2 bash`

## List installed plugins in container
`docker exec jenkins2 ls -l /usr/share/jenkins/ref/plugins`

## Run dev server for A app:
`docker run -p 52022:22 -tid --name a-dev docker-ssh-connection:1`

## Jenkins CLI
See CLI commands: http://localhost:8080/cli

### Connect via Jenkins CLI:
```shell
export JENKINS_URL=http://localhost:8080
wget "$JENKINS_URL/jnlpJars/jenkins-cli.jar"

java -jar jenkins-cli.jar -s $JENKINS_URL help
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs
java -jar jenkins-cli.jar -s $JENKINS_URL safe-restart
java -jar jenkins-cli.jar -s $JENKINS_URL restart
```

#### Login and password authorization
```shell
# Single command
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs --username aleks --password pass

# Many commands
java -jar jenkins-cli.jar -s $JENKINS_URL login --username aleks --password pass
java -jar jenkins-cli.jar -s $JENKINS_URL who-am-i
java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs
java -jar jenkins-cli.jar -s $JENKINS_URL logout
```
