# "Ansible Control Machine" is current OS, "Ansible Remote Machine" is Docker container

## Build Docker image
`docker build -t ansible-remote-machine-in-docker:1 .`


## Run Docker image
```
docker run -p 52022:22 -tid --name ansible-remote-machine-in-docker ansible-remote-machine-in-docker:1
docker exec -it ansible-remote-machine-in-docker bash
docker stop ansible-remote-machine-in-docker
docker rm ansible-remote-machine-in-docker
```

## Connect via SSH
`sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 22 root@localhost`
Command `sshpass -p 'screencast'` sets the root's password.
Command `-o StrictHostKeyChecking=no` adds the container to known_hosts.
