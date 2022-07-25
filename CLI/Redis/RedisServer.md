# Redis Server

## Install
Install Server: `sudo apt install -y redis-server`
Start Server: `sudo service redis-server start`
Show Server status: `sudo service redis-server status`
Stop Server: `sudo service redis-server stop`

## Configure
Configure Server: `sudo nano /etc/redis/redis.conf`

## Linux service (NOT WORK)
Show Redis service status: `sudo systemctl status redis`
Restart Redis service: `sudo systemctl restart redis.service`
Disable Redis service: `sudo systemctl disable redis`
