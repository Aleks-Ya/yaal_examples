# clamd CLI

## Docs
Configuration manual: `man clamd.conf`
Config file location: `/etc/clamav/clamd.conf`

## Install
`sudo apt install -y clamav-daemon`

## Commands
### Info
Help: `clamd --help`
Version: `clamd --version`

### Without Service
Run in foreground: `sudo clamd --foreground --debug`

### Service
Show service status: `sudo systemctl status clamav-daemon`
Restart service: `sudo systemctl restart clamav-daemon`
Stop service: `sudo systemctl stop clamav-daemon`
Start service: `sudo systemctl start clamav-daemon`
Show service log: `journalctl -u clamav-daemon`
Disable auto-start: `sudo systemctl disable clamav-daemon`
