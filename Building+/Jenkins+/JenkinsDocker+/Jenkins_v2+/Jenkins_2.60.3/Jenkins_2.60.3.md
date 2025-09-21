!!! DEPRECATED

# Jenkins v2

Source: https://hub.docker.com/_/jenkins/

## Run Docker container
```shell
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
### Connect via Jenkins CLI
#### Create alias
```shell
export JENKINS_URL="http://localhost:8080"
wget -O /var/tmp/jenkins-cli.jar "$JENKINS_URL/jnlpJars/jenkins-cli.jar"
alias jenkins="java -jar /var/tmp/jenkins-cli.jar -s $JENKINS_URL"
```

#### Commands
Help: `jenkins help`
List jobs: `jenkins list-jobs`
Restart (safe): `jenkins safe-restart`
Restart: `jenkins restart`

#### Login and password authorization
```shell
# Single command
jenkins list-jobs --username aleks --password pass

# Many commands
jenkins login --username aleks --password pass
jenkins who-am-i
jenkins list-jobs
jenkins logout
```

### See all CLI commands
http://localhost:8080/cli
