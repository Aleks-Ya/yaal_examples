# Fedora Docker image

## Build from Dockerfile
```
#Server
docker build -t kerberos-server server

#Client
docker build -t kerberos-client client
```

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`
