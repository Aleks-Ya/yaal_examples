# SSH connection between two Docker containers.

## Build Docker images:
```
docker build -t ssh-server -f ServerDockerfile .
docker build -t ssh-client -f ClientDockerfile .
```

## Run Docker images
```
docker run -p 52022:22 -tid --rm --name ssh-server ssh-server
docker run -ti --rm --name ssh-client --link ssh-server:server ssh-client /bin/bash
```

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
sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 22 root@server
```
### By private key
```
#Once
ssh-keygen -N "" -f /root/.ssh/id_rsa
sshpass -p 'screencast' ssh-copy-id root@server
#Each time
ssh root@server
```
