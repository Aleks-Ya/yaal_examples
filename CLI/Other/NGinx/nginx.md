# nginx CLI

Install: 
- APT: `sudo apt install -y nginx-full`
- YUM: `sudo yum install -y nginx`
Remove: `sudo apt remove -y nginx-full`

Help: `nginx -h`
Show version: `nginx -v`
Show version and configure options: `nginx -V`

Fast shutdown: `nginx -s stop`
Graceful shutdown: `nginx -s quit`
Reload configuration: `nginx -s reload`
Reopening the log files: `nginx -s reopen`
Test configuration file: `sudo nginx -t -c $PWD/nginx.conf`

## Start
Start NGinx with given config file:
1. Run: `sudo nginx -c $PWD/nginx.conf`
2. Test: `curl http://localhost`

## Daemon
Show status: `sudo systemctl status nginx`
Stop daemon: `sudo systemctl stop nginx`
Disable daemon: `sudo systemctl disable nginx`
