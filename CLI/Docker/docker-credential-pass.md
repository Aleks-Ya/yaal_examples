# docker-credential-pass

## Install
1. Install `pass` tool (see `CLI/Linux/pass.md`)
2. Download file `docker-credential-pass-v0.8.0.linux-amd64` from https://github.com/docker/docker-credential-helpers/releases
3. Rename the file into `/home/aleks/installed/docker-credential-pass/docker-credential-pass`
4. Add execution permission
5. Add `export PATH=$PATH:/home/aleks/installed/docker-credential-pass` to `~/.bashrc`
6. Configure Docker Engine
	1. File `$HOME/.docker/config.json`
	2. Set: `{XXX, "credsStore": "pass", XXX}`
	3. (?) Restart Docker Daemon: `sudo systemctl restart docker`

## Commands
Version: `docker-credential-pass version`
List credentials: `docker-credential-pass list`

## Errors
### `no usernames for https://index.docker.io/v1/`
Command: `docker-credential-pass list`
Error message: `no usernames for https://index.docker.io/v1/`
Solution: logout (`docker logout`) and login again (`docker login`)
