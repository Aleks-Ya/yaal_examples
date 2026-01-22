# dnf CLI

Update: `sudo dnf update`
Show last metadata refresh time: `sudo dnf check-update`

## Package
List installed packages:
- All: `dnf list installed`
- By regexp: `dnf list installed postgres*`
Install a package: `sudo dnf install gcc-c++`
Search package by name: `dnf search mysql`
Upgrade a package: `sudo dnf upgrade postgresql17`

## Repo
List repos: `dnf repolist`
