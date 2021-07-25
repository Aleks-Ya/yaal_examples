# CentOS Docker image

## Standard image
### Info
Sources: https://hub.docker.com/_/centos

### Run command
`docker run --rm --name centos7 centos:7 echo 'Hello, World!'`

### Run Bash
`docker run -it --rm --name centos7 centos:7 bash`

## Updated image
Standard image + yum update + install useful tools.

### CentOs 8
Build: `export V=8; docker build --build-arg VERSION=$V -t centos-updated:$V .`
Run: `docker run -it --rm --name centos-updated-8 centos-updated:8 bash`
### CentOs 7
Build: `export V=7; docker build --build-arg VERSION=$V -t centos-updated:$V .`
Run: `docker run -it --rm --name centos-updated-7 centos-updated:7 bash`
