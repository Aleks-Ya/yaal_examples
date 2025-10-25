# snap CLI

## Info
Help: `snap help`
Help about a command: `snap help find`
Show info about a snap: `snap info threat-dragon`

## Commands
List installed snaps: `snap list`
List available versions of a snap: `snap find postman`
Find snaps by phrase: `snap find "media player"`
Switch to the edge channel: `sudo snap switch --edge telegram-desktop`
Delete a snap: `sudo snap remove sublime-text`
Get a property value: `sudo snap get system refresh.retain`
Set a property value: `sudo snap set system refresh.retain=2`
Show info about a snap: `snap info opera`
Show info about a snap (detailed): `snap info --verbose opera`

## Run
Run a snap from CLI and watch logs: `snap run opera`
Connect to a snap with terminal: `snap run --shell firefox`
Execute a command in a snap terminal: `snap run --shell firefox -c 'getent hosts example.com'`

## Refresh
List available updates: `snap refresh --list`
Update versions of all installed snaps: `sudo snap refresh`

## Revert
Revert a snap to its state before the latest refres: `sudo snap revert sublime-text`

## Interfaces (Connections)
List connections of a snap: `snap connections gnome-boxes`

## Errors
### Cannot refresh snap-store
Command: `sudo snap refresh snap-store`
Message:
```
error: cannot refresh "snap-store": snap "snap-store" has running apps
       (snap-store), pids: 3156
```
Fix:
```shell
snap-store --quit
sudo snap refresh snap-store
```