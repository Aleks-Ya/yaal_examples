# Kerberos Docker image

## Standard image
Source: https://hub.docker.com/_/debian/
Run: `docker run -it --rm --name debian debian:stable bash`

## Updated image
Popular versions: `stable`, `11`
Choose version: `export V=stable`
Build: `docker build --build-arg VERSION=$V -t debian-updated:$V DebianUpdated`
Run: `docker run -it --rm --name debian-updated-$V debian-updated:$V`
Check: `docker run -it --rm --name debian-updated-$V debian-updated:$V cat /etc/os-release`