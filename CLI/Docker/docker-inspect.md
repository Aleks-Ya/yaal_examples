# docker-inspect CLI

Show container IP: `docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name>`
Show available memory (`0` means ulimited): `docker inspect --format='{{.HostConfig.Memory}}' <container_name>`
