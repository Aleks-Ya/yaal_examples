# Yandex.Disk Docker image

Build from Dockerfile: `docker build -t yandex-disk .`
Show version: `docker run --rm -it yandex-disk -v`


```
docker run --rm -it \
-v /home/aleks/.config:/root/.config/yandex-disk \
-v /home/aleks/tmp/yandex-disk-docker-data:/root/Yandex.Disk \
yandex-disk status
```



```
docker run --rm -it \
	-e EXCLUDE="Lubov" \
	--mount type=volume,source=yandex-disk-docker-volume,target=/root/.config/yandex-disk \
	-v /home/aleks/tmp/yandex-disk-docker-data:/root/Yandex.Disk \
	yandex-disk \
	token
```