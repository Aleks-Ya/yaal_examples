# Jenkins v2

Source: https://hub.docker.com/r/jenkins/jenkins

## Run Docker container

1. Build and run: `./build_and_run.sh 2.511`
2. Open: http://localhost:8080

## Attach with Bash

`docker exec -it jenkins2 bash`

## List installed plugins in container

`docker exec jenkins2 ls -l /usr/share/jenkins/ref/plugins`
