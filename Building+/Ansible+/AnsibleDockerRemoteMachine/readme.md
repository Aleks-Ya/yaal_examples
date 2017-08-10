# "Ansible Control Machine" is current OS, "Ansible Remote Machine" is Docker container

## Build Docker image
`docker build -t ansible-remote-machine-in-docker:1 .`

## Run Docker image
```
docker run -p 52023:22 -tid --name ansible-remote-machine-in-docker ansible-remote-machine-in-docker:1
docker exec -it ansible-remote-machine-in-docker bash
docker stop ansible-remote-machine-in-docker
docker rm ansible-remote-machine-in-docker
```

## Connect via SSH
### Prepare
```
export CONTAINER_IP=$(docker exec ansible-remote-machine-in-docker hostname -I)
sshpass -p 'screencast' ssh-copy-id root@$CONTAINER_IP
```
### Connect
```
ssh root@$CONTAINER_IP
```
