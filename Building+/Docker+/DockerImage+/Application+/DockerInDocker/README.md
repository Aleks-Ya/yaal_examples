# Docker in Docker

Source: https://hub.docker.com/_/docker/

1. Run: `docker run --rm --privileged --name docker-in-docker --network host docker:dind`
2. Check:`docker exec -it docker-in-docker docker --version`
