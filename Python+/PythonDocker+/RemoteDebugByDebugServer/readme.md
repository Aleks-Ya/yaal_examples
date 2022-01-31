# Python remote debug via Intellij Idea Debug Server

[Doc](https://www.jetbrains.com/help/pycharm/remote-debugging.html#python-debug-server)

## Run
### Prepare Idea project
1. Open `python_project` in Idea
1. Run `Python Remote Debug`
1. Wait connection from Python script in Docker
### Run Docker container
```
docker build -t aleks3490/python:3.6.5 .
docker run --rm -it --name python365 --net=host --mount type=bind,source="$(pwd)/python_project",target=/root/script_dir aleks3490/python:3.6.5
```
