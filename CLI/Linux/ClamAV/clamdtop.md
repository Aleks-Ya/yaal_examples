# clamdtop CLI

Help: `clamdtop -h`
Run UI: `clamdtop`

## Error
### VERSION command unavailable
Command: `clamdtop`
Message: `VERSION command unavailable, consider enabling it in the clamd configuration.`
Fix:
1. Open `sudo nano /etc/clamav/clamd.conf`
2. Set `EnableVersionCommand true`
3. Set `EnableStatsCommand true`
4. Restart: `sudo systemctl restart clamav-daemon`
