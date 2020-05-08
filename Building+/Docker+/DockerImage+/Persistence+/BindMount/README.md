# Docker Bind mount

## Mount a directory
```
docker run -it --rm --name bindtest \
	--mount type=bind,source="$(pwd)"/bind,target=/app \
	alpine:latest \
	cat /app/from_host.txt
```

## Mount a file
```
docker run -it --rm --name bindtest \
	--mount type=bind,source="$(pwd)"/bind/from_host.txt,target=/app/from_host_1.txt \
	alpine:latest \
	cat /app/from_host_1.txt
```

## Check that bind is mounted
```
docker run -it --rm -d --name bindtest --mount type=bind,source="$(pwd)"/bind,target=/app alpine:latest
docker inspect bindtest --format='{{json .HostConfig.Mounts}}'
docker stop bindtest
```
