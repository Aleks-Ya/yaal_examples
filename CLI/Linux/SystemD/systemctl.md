# systemctl CLI

## Info
Help: `systemctl -h`

## Commands
List services: `systemctl --type=service`
Show service status: `sudo systemctl status redis`
Start a service: `sudo systemctl start redis`
Stop a service: `sudo systemctl stop redis`
Restart a service: `sudo systemctl restart redis`
Disable a service (prevent auto-start after reboot): `sudo systemctl disable redis`
