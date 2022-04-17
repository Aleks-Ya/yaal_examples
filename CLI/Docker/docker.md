Docker CLI: inspect

Show IP of a container: 
```
docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name>
```


Docker CLI: network
List all networks: 
```
docker network ls
```
Show network details:
```
docker network inspect bridge
```
Create network: 
```
docker network create --driver bridge --subnet 192.168.100.0/24 --ip-range 192.168.100.0/24 my-bridge-network
```


Docker CLI: any

ps [docs](https://docs.docker.com/engine/reference/commandline/ps/)
```
docker ps              # See a list of all running containers
docker ps -a           # See a list of all containers, even the ones not running
```
run [docs](https://docs.docker.com/engine/reference/commandline/run/)
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
tag [docs](https://docs.docker.com/engine/reference/commandline/tag/)
```
docker tag friendlyhello aleks3490/temp:tag1
docker tag <image> username/repository:tag  # Tag <image> for upload to registry
```
version
```
docker version
```
image/images
```
docker images
docker images -a                               # Show all images on this machine
```
login
```
docker login             # Log in this CLI session using your Docker credentials
```
stop
```
docker stop 60ab118fc972
docker stop <hash> # Gracefully stop the specified container
```
kill
```
docker kill <hash> # Force shutdown of the specified container
```
push
```
docker push aleks3490/temp:tag1
docker push username/repository:tag            # Upload tagged image to registry
```
swarm
```
docker swarm init
```
stack
```
docker stack deploy -c docker-compose.yml getstartedlab
docker stack ps getstartedlab
```
rm/rmi
```
docker rm <hash>               # Remove the specified container from this machine
docker rm $(docker ps -a -q)   # Remove all containers from this machine
docker rmi <imagename>         # Remove the specified image from this machine
docker rmi $(docker images -q) # Remove all images from this machine
```
volume
```
docker volume ls #List all volumes
```

info

```
docker info -f '{{json .}}' | python -m json.tool # Print all available fields
docker info -f '{{.DockerRootDir}}'               # Print local repository location
```

cp

```
docker cp hdfs-master:/tmp/client.keytab /tmp/client.keytab # Copy single file from container to host
```

system

```
docker system prune  # Remove: all stopped containers, all not used networks, all dangling images, all dangling build cache
```
