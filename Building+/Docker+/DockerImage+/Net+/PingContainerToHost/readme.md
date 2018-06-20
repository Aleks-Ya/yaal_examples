# Connect from container to a service ran on host

## Option 1: network "bridge"
On host: `python -m SimpleHTTPServer 1091`
Run container: `docker run -ti --rm --name ping alpine:latest`
From container: `nc -zv 172.17.0.1 1091`

## Option 2: network "host"
