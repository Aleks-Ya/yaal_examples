# Linking containers: Ping one container from another container.

## Run Docker images
```shell
docker run -ti --rm --name ping-server alpine:latest
docker run -ti --rm --name ping-client --link ping-server:server alpine:latest ping -c 3 server
```
