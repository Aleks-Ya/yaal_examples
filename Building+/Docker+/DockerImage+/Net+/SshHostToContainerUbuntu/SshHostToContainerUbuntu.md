# A Docker container ready to SSH connection (based on Ubuntu).

## Build Docker image:
`docker build -t ssh-host-to-container-ubuntu .`

## Run Docker image
`docker run -p 52022:22 -tid --name ssh ssh-host-to-container-ubuntu`

## Show container's IP
`docker exec ssh hostname -I`

## Connect via bash
`docker exec -it ssh bash`

## Remove container
```
docker stop ssh
docker rm ssh
```

## Connect via SSH
### By password
Command "sshpass -p 'screencast'" sets the root's password.
Command "-o StrictHostKeyChecking=no" adds the container to known_hosts.
```
export CONTAINER_IP=$(docker exec ssh hostname -I)
sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 22 root@$CONTAINER_IP
```
### By private key
```
export CONTAINER_IP=$(docker exec ssh hostname -I)
sshpass -p 'screencast' ssh-copy-id root@$CONTAINER_IP
ssh root@$CONTAINER_IP
```
