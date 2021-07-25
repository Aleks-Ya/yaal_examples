# Run Alpine Linux

## Standard image

Source: https://hub.docker.com/r/_/alpine/
Run: `docker run -ti --rm alpine:latest`

## Updated image

Build latest: 
```
export V=latest; docker build --build-arg VERSION=$V -t alpine-updated:$V .
```  
Run: `docker run -it alpine-updated:latest bash`
