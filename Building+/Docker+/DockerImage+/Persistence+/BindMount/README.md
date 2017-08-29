# Doicker Bind mount
```
# 1. Run container and check file /app/from_host.txt
docker run -it --rm --name bindtest --mount type=bind,source="$(pwd)"/bind,target=/app alpine:latest

# 2. Check that bind is mounted
docker container inspect bindtest
```
