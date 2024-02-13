# clamonacc CLI

## Commands
Help: `clamonacc -h`
Man page: `man clamonacc`
Version: `sudo clamonacc --version`
Start: `sudo clamonacc`
Start (foreground): `sudo clamonacc --foreground`
Stop: `sudo kill $(pidof clamonacc)`

## Logs
Follow logs: `sudo less -f /var/log/clamav/clamav.log`

## Troubleshooting
### Prevent infinite event scanning loops
Command: `sudo clamonacc`
Message: `ERROR: Clamonacc: at least one of OnAccessExcludeUID, OnAccessExcludeUname, or OnAccessExcludeRootUID must be specified ... it is reccomended you exclude the clamd instance UID or uname to prevent infinite event scanning loops`
Fix: append `OnAccessExcludeUname clamav` to `/etc/clamav/clamd.conf`

### OnAccessIncludePath
Command: `sudo clamonacc --foreground`
Message: `ERROR: ClamInotif: Please specify at least one path with OnAccessIncludePath`
Fix: append `OnAccessIncludePath /home/aleks/Music` to `/etc/clamav/clamd.conf`

###
Message: `Address all issues found in one of the product(s) listed below: ClamAV. Real time protection is disabled. Enable real time protection in your anti-malware settings.`
Fix: append `OnAccessPrevention yes` to `/etc/clamav/clamd.conf`
