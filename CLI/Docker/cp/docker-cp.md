# docker-cp

Docs: https://docs.docker.com/engine/reference/commandline/cp/

## Copy from host into container
### File
```shell
docker run -it --rm -d --name cptest alpine:latest
docker cp data.txt cptest:/tmp/data1.txt
docker exec -it cptest cat /tmp/data1.txt
docker stop cptest
```
### Folder
```shell
docker run -it --rm -d --name cptest alpine:latest
docker cp data_dir cptest:/tmp/
docker exec -it cptest cat /tmp/data_dir/hi.txt
docker stop cptest
```

## Copy from container to host
### File
```shell
docker run -it --rm -d --name cptest alpine:latest
docker cp cptest:/etc/os-release /tmp/os-release-1
cat /tmp/os-release-1
rm /tmp/os-release-1
docker stop cptest
```
### Folder
```shell
docker run -it --rm -d --name cptest alpine:latest
docker cp cptest:/etc/ssl /tmp/ssl_cptest
ls /tmp/ssl_cptest
rm -r /tmp/ssl_cptest
docker stop cptest
```
