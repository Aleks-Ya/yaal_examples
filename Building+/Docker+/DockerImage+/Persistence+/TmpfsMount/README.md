# Doicker tmpfs mount
```
# 1. Run container and create file in /app dir (tmpfs) and in another dir (usual)
docker run -it --name tmptest --mount type=tmpfs,destination=/app alpine:latest

# 2. Check that tmpfs is mounted
docker container inspect tmptest

# 3. Stop container. Tmpfs folder will be cleaned, but another folders will be kept.
docker stop tmptest

# 4. Run container
docker start tmptest

# 5. Check that /app dir is empty and the another dir is kept.
docker exec -it tmpfs sh
```
