# Run Docker Registry locally

Doc: https://docs.docker.com/registry/deploying/

## Run
No restart: `docker run -d -p 5000:5000 --name registry registry:2`

With restart: `docker run -d -p 5000:5000 --restart=always --name registry registry:2`

## Info
Local Registry URL: `localhost:5000`
List images in the local Registry: `docker images localhost:5000/*`

## Push to local Registry
```
docker pull alpine:latest
docker tag alpine:latest localhost:5000/alpine-tmp
docker push localhost:5000/alpine-tmp
```
