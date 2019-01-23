# Dockerfile HEALTHCHECK instruction

# Build
`docker build --tag health-check .`

# Run
`docker run --rm -it --name health-check health-check`

# See status
1. `docker ps`
1. `docker inspect health-check`

# Change status
In bash:
- to "unhealthy": `rm $STATUS_FILE`
- to "healthy": `touch $STATUS_FILE`
