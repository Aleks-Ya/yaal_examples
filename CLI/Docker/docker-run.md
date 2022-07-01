# docker-run

Docs: https://docs.docker.com/engine/reference/commandline/run/

## Commands
Run HelloWorld example: `docker run hello-world`
Run Ubuntu: `docker run -it ubuntu bash`
Map port: `docker run -p 4000:80 friendlyhello`
Map port and run as a daemon (detached mode): `docker run -d -p 4000:80 friendlyhello`
Run Ubuntu and execute a command: `docker run ubuntu echo "hello world" docker run -ti centos /bin/bash`
Run an image tag: `docker run -p81:80 aleks3490/temp:tag1`
Run an image from Registry: `docker run username/repository:tag`

### Mount
Mount a host directory to a container directory: `docker run --rm -it --mount type=bind,src=/host/dir,dst=/container/dir almalinux bash`
Mount a host file to a container file: `docker run --rm -it --mount type=bind,src=/host/file.txt,dst=/container/file.txt almalinux bash`