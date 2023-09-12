# CentOS Docker image (DISCONTINUED)

## Standard image
Sources: https://hub.docker.com/_/centos
Run a command:`docker run --rm --name centos7 centos:7 echo 'Hello, World!'`
Run Bash:`docker run -it --rm --name centos7 centos:7 bash`

## Updated image
Popular versions: `7`, `8`
Choose version: `export V=8`
Build: `docker build --build-arg VERSION=$V -t centos-updated:$V CentOsUpdated`
Run: `docker run -it --rm --name centos-updated-$V centos-updated:$V`
Check: `docker run -it --rm --name centos-updated-$V centos-updated:$V which telnet`
