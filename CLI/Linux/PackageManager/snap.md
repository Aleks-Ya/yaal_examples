# snap CLI

## Info
Help: `snap help`
Help about a command: `snap help find`
Show info about a snap: `snap info threat-dragon`

## Commands
List installed snaps: `snap list`
List available versions of a snap: `snap find postman`
Switch to the edge channel: `sudo snap switch --edge telegram-desktop`
Delete a snap: `sudo snap remove sublime-text`
Get a property value: `sudo snap get system refresh.retain`
Set a property value: `sudo snap set system refresh.retain=2`
List connections of a snap: `snap connections gnome-boxes`

## Refresh
List available updates: `snap refresh --list`
Update versions of all installed snaps: `sudo snap refresh`

## Revert
Revert a snap to its state before the latest refres: `sudo snap revert sublime-text`

## Errors
### Cannot refresh snap-store
Command: `sudo snap refresh snap-store`
Message:
```
error: cannot refresh "snap-store": snap "snap-store" has running apps
       (snap-store), pids: 3156
```
Fix:
```
snap-store --quit
sudo snap refresh snap-store
```