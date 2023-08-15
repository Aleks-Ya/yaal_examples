# WSL (Windows Subsystem for Linux)

Help: `wsl --help`
Version: `wsl --version`
Show status: `wsl --status`
Update WSL: `wsl --update`

## Distributions
List distributions available to install: `wsl --list -o`
Set default distribution: `wsl -s Ubuntu`
Delete a distribution: `wsl --unregister Ubuntu`
Set detaulf user: `wsl --set-default-user aleks`

## Inside a distribution
Exit: `exit`

## Create a new user (don't work)
1. Create a user: `sudo adduser john`
2. Add the user to SUDO: `sudo usermod -aG sudo john`
3. Set as default user: ???
