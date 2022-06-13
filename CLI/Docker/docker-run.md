# docker-run

Docs: https://docs.docker.com/engine/reference/commandline/run/

Commands:
```
docker run hello-world
docker run -it ubuntu bash
docker run -p 4000:80 friendlyhello
docker run -d -p 4000:80 friendlyhello
docker run ubuntu echo "hello world" docker run -ti centos /bin/bash    
docker run -p81:80 aleks3490/temp:tag1
docker run -p 4000:80 friendlyname  # Run "friendlyname" mapping port 4000 to 80
docker run -d -p 4000:80 friendlyname # Same thing, but in detached mode
docker run username/repository:tag                   # Run image from a registry
```
