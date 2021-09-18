# Run Alpine Linux

## Standard image
Source: https://hub.docker.com/r/_/alpine/
Run: `docker run -it --rm alpine:latest`

## Updated image
Popular versions: `latest`, `3`
Choose version: `export V=3`
Build: `docker build --build-arg VERSION=$V -t alpine-updated:$V AlpineUpdated`
Run: `docker run -it --rm --name alpine-updated-$V alpine-updated:$V`
Check: `docker run -it --rm --name alpine-updated-$V alpine-updated:$V bash --version`
