#!/bin/bash

dnf update -y
dnf install docker -y
systemctl start docker
systemctl enable docker
usermod -a -G docker ec2-user

docker run -d -p 80:80 caddy caddy respond -l :80 -b "Hello, Caddy"