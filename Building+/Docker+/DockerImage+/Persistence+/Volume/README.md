# Doicker Volume

## Declare a volume
### Declare volume in Dockerfile
```
docker build -t volume_test .
docker run -it volume_test cat /myvol/greeting.txt
```

### Declare volume in Run command
```
docker run -it --rm --name volume_test_run --mount type=volume,source=my_volume,target=/myvol alpine:latest
```

## Other
### Inspect
```
#Inspect container
docker inspect volume_test

#Inspect volume
docker volume inspect my_volume
```

### Manipulations with volumes
```
#Create a volume
docker volume create my-vol

#List volumes
docker volume ls

#Inspect volume
docker volume inspect my_volume

#Delete volume
docker volume rm my-vol
```
