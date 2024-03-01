# Docker Daemon CLI

Docs: https://docs.docker.com/engine/reference/commandline/dockerd/

## Commands
Help: `dockerd -h`
Show Docker service status: `sudo systemctl status docker`
Restart Docker service: `sudo systemctl restart docker`
Limit concurrent downloads on pull: `dockerd --max-concurrent-downloads 1`
Debug mode: `dockerd --debug`
Define config file (default value): `dockerd --config-file /etc/docker/daemon.json`
Log level ("debug"|"info"|"warn"|"error"|"fatal"): `dockerd --log-level info`

## Make repo insecure
Add to `/etc/docker/daemon.json`: `{ "insecure-registries" : ["myregistrydomain.com:5000"] }`
