# SSH connection between two Docker containers.

## Start
```
#Common
docker-compose up

#Force rebuild
docker-compose up --build
```

## Show all processes
`docker-compose ps`

## Attach to client with Bash
`docker exec -ti ssh-client /bin/bash`

## Connect via SSH (from Bash on client)
```
#By password
sshpass -p 'screencast' ssh root@server

#By private key
ssh root@server
```

## Stop
`docker-compose down`
