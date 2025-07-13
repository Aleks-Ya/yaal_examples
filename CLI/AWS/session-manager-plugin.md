# session-manager-plugin CLI

## Install
Docs: https://docs.aws.amazon.com/systems-manager/latest/userguide/install-plugin-debian-and-ubuntu.html

Install:
1. Download DEB: `curl "https://s3.amazonaws.com/session-manager-downloads/plugin/latest/ubuntu_64bit/session-manager-plugin.deb" -o "session-manager-plugin.deb"`
2. Install DEB: `sudo dpkg -i session-manager-plugin.deb`
3. Verify: `session-manager-plugin`

Uninstall: `sudo dpkg -r session-manager-plugin`

## Command
Verify installation: `session-manager-plugin`
