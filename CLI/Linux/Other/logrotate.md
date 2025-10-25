# logrotate CLI

Config file locations:
- `/etc/logrotate.conf`
- `/etc/logrotate.d/*`

Run for a specific config file: `logrotate /etc/logrotate.d/rich_robot`
Dry-run (-d) for a specific config file: `logrotate -d /etc/logrotate.d/rich_robot`
See rotation history: `cat /var/lib/logrotate/status`
