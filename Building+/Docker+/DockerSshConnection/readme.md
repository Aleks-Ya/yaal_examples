# A Docker container ready to SSH connection.

## Build Docker image:
`docker build -t docker-ssh-connection:1 .`

## Run Docker image
`docker run -p 52022:22 -tid --name ssh docker-ssh-connection:1`

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
