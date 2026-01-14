# systemctl CLI

## Info
Help: `systemctl -h`
Help for a command: no

## Service
List services: `systemctl --type=service`
Show service status: `sudo systemctl status redis`
Start a service: `sudo systemctl start redis`
Stop a service: `sudo systemctl stop redis`
Restart a service: `sudo systemctl restart redis`
Disable a service (prevent auto-start after reboot): `sudo systemctl disable redis`
Enable and start a service: `systemctl --user enable --now redis`

## Unit
List units: `systemctl list-units --all`
List unit files: `systemctl list-unit-files`
List targets: `systemctl list-units --type=target`
List scopes: `systemctl list-units --type=scope`
