# freshclam CLI

## Commands
Refresh signatures: `sudo freshclam`

## Service
Status: `sudo systemctl status clamav-freshclam`
Stop: `sudo systemctl stop clamav-freshclam`
Disable auto-start: `sudo systemctl disable clamav-freshclam`

## Errors
### freshclam.log is locked by another process
Command: `sudo freshclam`
Message:
```
aleks@aleks-pc:~$ sudo freshclam
ERROR: /var/log/clamav/freshclam.log is locked by another process
ERROR: Problem with internal logger (UpdateLogFile = /var/log/clamav/freshclam.log).
ERROR: initialize: libfreshclam init failed.
ERROR: Initialization error!
```
Solution: `sudo systemctl stop clamav-freshclam.service`
