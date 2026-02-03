Docker CLI: any

## Help
Help: `docker --help`
Help for command: `docker build --help`

## Commands

ps [docs](https://docs.docker.com/engine/reference/commandline/ps/)
```shell
docker ps              # See a list of all running containers
docker ps -a           # See a list of all containers, even the ones not running
```

tag [docs](https://docs.docker.com/engine/reference/commandline/tag/)
```shell
docker tag existing_image aleks3490/new:tag1
docker tag <image> username/repository:tag  # Tag <image> for upload to registry
```

version
```shell
docker version
```

image/images
```shell
docker images
docker images -a                              	# Show all images on this machine
docker image inspect hello-world				#Show details about an image
```

login
```shell
docker login             # Log in this CLI session using your Docker credentials
```

stop
```shell
docker stop 60ab118fc972
docker stop <hash> # Gracefully stop the specified container
```

kill
```shell
docker kill <hash> # Force shutdown of the specified container
```

push
```shell
docker push aleks3490/temp:tag1
docker push username/repository:tag            # Upload tagged image to registry
```

swarm
```shell
docker swarm init
```

stack
```shell
docker stack deploy -c docker-compose.yml getstartedlab
docker stack ps getstartedlab
```

rm/rmi
```shell
docker rm <hash>               # Remove the specified container from this machine
docker rm $(docker ps -a -q)   # Remove all containers from this machine
docker rmi <tag>			   # Remove a tag from an image
docker rmi <imagename>         # Remove the specified image from this machine
docker rmi $(docker images -q) # Remove all images from this machine
```

system
```shell
docker system prune  # Remove: all stopped containers, all not used networks, all dangling images, all dangling build cache
```
