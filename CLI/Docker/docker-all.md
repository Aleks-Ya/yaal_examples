Docker CLI: any

ps [docs](https://docs.docker.com/engine/reference/commandline/ps/)
```
docker ps              # See a list of all running containers
docker ps -a           # See a list of all containers, even the ones not running
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
system

```
docker system prune  # Remove: all stopped containers, all not used networks, all dangling images, all dangling build cache
```
