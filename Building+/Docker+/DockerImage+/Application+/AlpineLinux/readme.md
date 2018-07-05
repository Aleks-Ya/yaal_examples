# Run Alpine Linux

Source: https://hub.docker.com/r/_/alpine/

## Run exists image:
`docker run -ti --rm alpine:latest`

## Build from Dockerfile and run
```
docker build -t my-alpine .
docker run -ti --rm my-alpine
```
