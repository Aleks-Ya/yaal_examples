# clamd CLI

## Docs
Configuration manual: `man clamd.conf`
Config file location: `/etc/clamav/clamd.conf`

## Install
`sudo apt install -y clamav-daemon`

## Commands
Help: `clamd --help`
Version: `clamd --version`
Show service status: `service clamav-daemon status`
Restart service: `sudo service clamav-daemon restart`
Stop service: `sudo service clamav-daemon stop`
Start service: `sudo service clamav-daemon start`
Show service log: `journalctl -u clamav-daemon`
Disable auto-start: `sudo systemctl disable clamav-daemon`
