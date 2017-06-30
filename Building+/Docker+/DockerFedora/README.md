# Fedora Docker image

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`

## Build from Dockerfile
`docker build --network default .`
