# Run CDH 5.7 locally by Docker

Sources: https://hub.docker.com/r/cloudera/quickstart

Pull: docker pull cloudera/quickstart:latest

## Start
1. Run Docker images:
```
docker run --name=cdh --hostname=quickstart.cloudera --privileged=true -t -i \
	-p 8888:8888 -p 7180:7180 -p 8080:80 -p 11000:11000 -p 14000:14000 -p 14001:14001 -p 12000:12000 -p 12001:12001 \
	cloudera/quickstart /usr/bin/docker-quickstart
```
2. Run Cloudera Manager (from Docker): `/home/cloudera/cloudera-manager --express`

Links:
 - Hue: http://localhost:8888 (login: `claudera`, password: `cloudera`)
 - Oozie console: http://localhost:11000/oozie/
 - Tutorial: http://localhost:8080/
 - WebHDFS: http://localhost:14000/webhdfs/v1
 - Sqoop: http://localhost:12000/
 - Cloudera Manager: http://localhost:7180  (login: `claudera`, password: `cloudera`)
