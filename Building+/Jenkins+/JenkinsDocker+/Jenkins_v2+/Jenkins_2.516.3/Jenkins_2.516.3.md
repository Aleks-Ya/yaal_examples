# Jenkins v2

Source: https://hub.docker.com/r/jenkins/jenkins

## Run Docker container
1. Set version in `version.txt` (list version at https://www.jenkins.io/download)
2. Build: `./build.sh`
3. Run: `./run.sh`
4. Open: http://localhost:8080

## Attach with Bash
`docker exec -it jenkins2 bash`

## List installed plugins in container
`docker exec jenkins2 ls -l /usr/share/jenkins/ref/plugins`

## Open Jenkins in browser
http://localhost:8080

## Run dev server for A app
`docker run -p 52022:22 -tid --name a-dev docker-ssh-connection:1`

## Jenkins CLI
See `CLI/Jenkins/JenkinsCli/jenkins-cli.md`
