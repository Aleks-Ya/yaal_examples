# Docker Daemon CLI

Docs: https://docs.docker.com/engine/reference/commandline/dockerd/
Help: 
```
dockerd -h
```
Restart docker service:
```
sudo service docker restart
```
Limit concurrent downloads on pull: 
```
dockerd --max-concurrent-downloads 1
```
Debug mode: 
```
dockerd --debug
```
Define config file (default value): 
```
dockerd --config-file /etc/docker/daemon.json
```
Log level ("debug"|"info"|"warn"|"error"|"fatal"): 
```
dockerd --log-level info
```
Make repo insecure:
```
#Add to /etc/docker/daemon.json
{ "insecure-registries" : ["myregistrydomain.com:5000"] }
```
