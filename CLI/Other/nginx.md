# nginx CLI

Install: `sudo apt install -y nginx-full`
Remove: `sudo apt remove -y nginx-full`

Help: `nginx -h`
Show version: `nginx -v`
Show version and configure options: `nginx -V`

Fast shutdown: `nginx -s stop`
Graceful shutdown: `nginx -s quit`
Reload configuration: `nginx -s reload`
Reopening the log files: `nginx -s reopen`
Test configuration file: `nginx -t -c nginx.conf`
