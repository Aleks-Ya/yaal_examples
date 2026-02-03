# flatpak CLI

## Install
1. Install the package: `sudo apt install -y flatpak`
2. Add default repo: `sudo flatpak remote-add --if-not-exists flathub https://dl.flathub.org/repo/flathub.flatpakrepo`

## Commands
### Help
Help: `flatpak --help`
Help for command: `flatpak update --help`

### Info
List installed apps: `flatpak list`
List running apps: `flatpak ps`
Search apps: `flatpak search firefox`

### Install
Install an app: `flatpak install org.mozilla.firefox`
Connect a running app by Bash: `flatpak enter com.getpostman.Postman bash`

### Update
Update all apps: `flatpak update -y`
Update one app: `flatpak update com.getpostman.Postman`

## Applications to install
Firefox: `org.mozilla.firefox`
ThunderBird: `org.mozilla.Thunderbird`
Postman: `com.getpostman.Postman`
