# ftp-proxy CLI

Install: `sudo apt install -y ftp-proxy`

## Config
Default: `/etc/proxy-suite/ftp-proxy.conf`

## Commands
Run with given config: `sudo ftp-proxy -f proxy.conf`
Run in foreground: `sudo ftp-proxy -d`
Print configuration from a file: `ftp-proxy -c -f proxy.conf`

## Log
Read logs: `sudo tail -f /var/log/tinyproxy/tinyproxy.log`

## Service
See status: `systemctl status ftp-proxy`
Disable autostart: `sudo systemctl disable ftp-proxy`
Stop service: `sudo systemctl stop ftp-proxy`