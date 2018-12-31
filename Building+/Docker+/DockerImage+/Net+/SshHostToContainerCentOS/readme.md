# A Docker container ready to SSH connection (based on CentOS).

## Info
Source: https://docs.docker.com/engine/examples/running_ssh_service/

## Build Docker image:
`docker build -t ssh-host-to-container-centos .`

## Run Docker image
`docker run --rm --name ssh-centos ssh-host-to-container-centos`

## Show container's IP
`docker exec ssh-centos hostname -I`

## Connect via bash
`docker exec -it ssh-centos bash`

## Remove container
```
docker stop ssh-centos
docker rm ssh-centos
```

## Connect via SSH
### By password
Command "sshpass -p 'screencast'" sets the root's password.
Command "-o StrictHostKeyChecking=no" adds the container to known_hosts.
```
export CONTAINER_IP=$(docker exec ssh-centos hostname -I)
ssh-keygen -R $CONTAINER_IP
sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 22 root@$CONTAINER_IP
```
### By private key
```
export CONTAINER_IP=$(docker exec ssh-centos hostname -I)
ssh-keygen -R $CONTAINER_IP
sshpass -p 'screencast' ssh-copy-id root@$CONTAINER_IP
ssh root@$CONTAINER_IP
```
