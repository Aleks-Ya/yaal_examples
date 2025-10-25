# logger CLI

Help: `logger --help`

## Logging
Log a message: `logger "Hello logger"`
Log with a tag: `logger -t alex1 "Hello logger"` -> `alex1: Hello logger`
Print all env vars to log: `printenv | logger`

## Read the log
From file: `tail -f /var/log/syslog`
From daemon: `journalctl -f`
Only tagged rows: `journalctl -f -t alex1`
