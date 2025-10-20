# A Docker container ready to SSH connection (based on CentOS).

## Info
Source: https://docs.docker.com/engine/examples/running_ssh_service/

## Build Docker image:
`./build.sh`

## Run Docker image
`./run.sh`

## Show container's IP
`docker exec ssh-centos hostname -I`

## Connect via bash
`docker exec -it ssh-centos bash`

## Stop container
`docker stop ssh-centos`

## Connect via SSH
### By password
Command "sshpass -p 'screencast'" sets the root's password.
Command "-o StrictHostKeyChecking=no" adds the container to known_hosts.
```shell
export CONTAINER_IP=$(docker exec ssh-centos hostname -I)
ssh-keygen -R $CONTAINER_IP
sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 22 root@$CONTAINER_IP
```
### By private key
```shell
export CONTAINER_IP=$(docker exec ssh-centos hostname -I)
ssh-keygen -R $CONTAINER_IP
sshpass -p 'screencast' ssh-copy-id root@$CONTAINER_IP
ssh root@$CONTAINER_IP
```
