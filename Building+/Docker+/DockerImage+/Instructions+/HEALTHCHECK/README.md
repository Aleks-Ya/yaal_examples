# Dockerfile HEALTHCHECK instruction

# Build
`docker build --tag health-check .`

# Run
`docker run --rm -it --name health-check health-check`

# See status
1. `docker ps`
2. `docker inspect --format='{{json .State.Health}}' health-check | jq`

# Change status
In bash:
- to "unhealthy": `rm $STATUS_FILE`
- to "healthy": `touch $STATUS_FILE`
