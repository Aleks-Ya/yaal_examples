Control Machine: 
docker build -f AnsbileControlMachineDockerfile -t ansible-control-machine:alpine-1 .
docker run --rm -ti ansible-control-machine:alpine-1 /bin/bash
Ansible version: 2.4.0

Remote Machine: 
docker build -f AnsbileRemoteMachineDockerfile -t ansible-remote-machine:alpine-1 .
docker run --rm -ti ansible-remote-machine:alpine-1 /bin/bash

