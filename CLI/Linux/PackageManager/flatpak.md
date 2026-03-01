# flatpak CLI

## Install
1. Install the package: `sudo apt install -y flatpak`
2. Add default repo: `sudo flatpak remote-add --if-not-exists flathub https://dl.flathub.org/repo/flathub.flatpakrepo`

## Commands
### Help
Help: `flatpak --help`
Help for command: `flatpak update --help`

### Info
List installed apps and runtimes: `flatpak list`
List installed apps: `flatpak list --app`
List running apps: `flatpak ps`
Search apps: `flatpak search firefox`
Show details about an app: `flatpak info com.getpostman.Postman`
History of changes: `flatpak history`

### Remote
List SYSTEM remote repositories: `flatpak remotes`
List USER remote repositories: `flatpak remotes -u`
Show app details from a repository: `flatpak remote-info flathub org.freedesktop.Sdk/x86_64/25.08`

### Run
Run an app and see logs: `flatpak run com.getpostman.Postman`

### Install
Install an app: `flatpak install org.mozilla.firefox`
Connect a running app by Bash: `flatpak enter com.getpostman.Postman bash`
Fix inconsistences: `flatpak repair`

### Update
Update all apps: `flatpak update -y`
Update one app: `flatpak update com.getpostman.Postman`

### Uninstall
Delete an app: `flatpak uninstall postman`
Delete unused runtimes and extensions: `flatpak uninstall --unused`

## Applications to install
Firefox: `org.mozilla.firefox`
ThunderBird: `org.mozilla.Thunderbird`
Postman: `com.getpostman.Postman`
DBeaver: `io.dbeaver.DBeaverCommunity`
