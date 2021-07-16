# Dockerfile ARG instruction

## Build
`docker build --tag instruction-arg --build-arg ALPINE_VERSION=3.12.7 --build-arg PERSON=John .`

## Run
`docker run -it --rm instruction-arg`
Output:
`Hello, John, from Alpine-3.12.7`
