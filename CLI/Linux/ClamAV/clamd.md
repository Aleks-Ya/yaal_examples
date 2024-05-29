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

## Errors
### Could not create socket directory
Command: `sudo clamd --foreground --debug`
Message:
```
setrlimit: Operation not permitted
Thu May 30 06:07:16 2024 -> !LOCAL: Could not create socket directory: /var/run/clamav: Permission denied
Thu May 30 06:07:16 2024 -> !LOCAL: Socket file /var/run/clamav/clamd.ctl could not be bound: No such file or directory
```
Fix:
```
sudo mkdir -p /var/run/clamav
sudo chown clamav:clamav /var/run/clamav
```
