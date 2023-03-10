# journalctl CLI

Help: `journalctl -h`
Version: `journalctl --version`
Show full log for a service: `journalctl -u clamav-daemon`
Follow log for a service: `journalctl -f -u clamav-daemon`
Show size of logs: `journalctl --disk-usage`
Remove logs until they have 500M size: `sudo journalctl --vacuum-size=500M`
