# Run Alpine Linux

## Standard image
Source: https://hub.docker.com/r/_/alpine/
Run: `docker run -it --rm alpine:latest`

## Updated image
Build: `docker build -t alpine-updated AlpineUpdated`
Run: `docker run -it --rm --name alpine-updated alpine-updated`
Check: `docker run -it --rm --name alpine-updated alpine-updated bash --version`
