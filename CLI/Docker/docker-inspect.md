# docker-inspect CLI

Show IP of a container: 
```
docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name>
```
Show memory: `docker inspect --format='{{.HostConfig.Memory}}' <container_name>` (`0` means ulimited)