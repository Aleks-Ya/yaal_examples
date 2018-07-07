# Dockerfile COPY instruction

## Build from Dockerfile
`docker build --tag instruction-copy .`

## Print copied file's content
`docker run -it --rm instruction-copy cat /tmp/data.txt`
