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
Build: `docker build --build-arg VERSION=8 -t centos-updated:8 .`
Run: `docker run -it --rm --name centos-updated-8 centos-updated:8 bash`
### CentOs 7
Build: `docker build --build-arg VERSION=7 -t centos-updated:7 .`
Run: `docker run -it --rm --name centos-updated-7 centos-updated:7 bash`
