# Idle Notification
Show a notification when the user is idle.

## Run manually
Default parameters: `~/pr/home/yaal_examples/Python+/Python3/src/apps/inactivity_time/idle_notify.py`
Custom parameters: `~/pr/home/yaal_examples/Python+/Python3/src/apps/inactivity_time/idle_notify.py --idle-seconds 30`

## Run as a service

### Run
1. `ln -sf ~/pr/home/yaal_examples/Python+/Python3/src/apps/inactivity_time/idle-notify.service ~/.config/systemd/user/idle-notify.service`
2. Reload daemon: `systemctl --user daemon-reload`
3. Verify status: `systemctl --user status idle-notify.service`

### Restart
After modifying `idle-notify.service`:
1. Reload daemon: `systemctl --user daemon-reload`
2. Verify status: `systemctl --user status idle-notify.service`

### Logs
`journalctl --user -u idle-notify.service -f`