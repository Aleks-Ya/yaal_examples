# Ubuntu Docker image

## Standard image
Source: https://hub.docker.com/_/ubuntu
Run: `docker run --rm -it ubuntu:22.04 bash`

## Updated image
Popular versions: `latest` (latest LTS), `18.04`.
Choose version: `export V=18.04`
Build: `docker build --build-arg VERSION=$V -t ubuntu-updated:$V UbuntuUpdated`
Check: `docker run -it --rm --name ubuntu-updated-$V ubuntu-updated:$V cat /etc/os-release`
Run: `docker run -it --rm --name ubuntu-updated-$V ubuntu-updated:$V`

