# Fedora Docker image

## Build from Dockerfile
```
#Server
docker build server

#Client
docker build client
```

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`
