Docker CLI: build

[Docs](https://docs.docker.com/engine/reference/commandline/build/)

Create image using this directory's Dockerfile: `docker build -t friendlyhello .`
Ignore cache: `docker build --no-cache .`
Specify Dockerfile location: `docker build -t friendlyhello -f path/to/Dockerfile .`
