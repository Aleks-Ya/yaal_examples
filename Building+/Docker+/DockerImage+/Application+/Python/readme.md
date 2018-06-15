# Python

[Official Docker container](https://hub.docker.com/r/_/python/)
[Git repo](https://github.com/docker-library/python)

## Run
### Run exists image
```
# Execute a Python script from a string
docker run --rm --name python365 python:3.6.5-alpine3.6 python -c "print('hi')"
```
### Run bash from image with installed Python packages
```
docker build -t aleks3490/python:3.6.5 .
docker run --rm -it --name python365 aleks3490/python:3.6.5
```
