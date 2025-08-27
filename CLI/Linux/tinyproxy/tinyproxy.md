# tinyproxy CLI

Install: `sudo apt install -y tinyproxy`

## Commands
Help: `tinyproxy -h`
Start with custom config: `sudo tinyproxy -c httpbin.conf`
Start in foreground: `sudo tinyproxy -d`

## Log
Read logs: `sudo tail -f /var/log/tinyproxy/tinyproxy.log`

## Service
See status: `systemctl status tinyproxy`
Disable autostart: `sudo systemctl disable tinyproxy`
Stop service: `sudo systemctl stop tinyproxy`

## Config
Default location: `/etc/tinyproxy/tinyproxy.conf`
