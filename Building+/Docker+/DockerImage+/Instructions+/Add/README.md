# Dockerfile ADD instruction

## Build from Dockerfile
`docker build --tag instruction-add .`

## Print copied file's content
`docker run -it --rm instruction-add cat /tmp/data.txt`
