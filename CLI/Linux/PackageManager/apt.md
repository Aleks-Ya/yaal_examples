# apt CLI

## apt
Help: `apt`
Version: `apt --version`
Search a package: `apt search my-package`
List all version of a package: `apt list -a my-package`
List history of installed packages: `grep "install " /var/log/dpkg.log`
Remove unused packages: `sudo apt-get autoclean && sudo apt-get autoremove`
List installed package: `apt list --installed`
Show package details: `apt show firefox`
Export a key to a file: `sudo apt-key export EE2C95AB58DC2B0138D16B4FEFC4571D7C90E5AF > yandex_disk.gpg`
Delete a key: `sudo apt-key del EE2C95AB58DC2B0138D16B4FEFC4571D7C90E5AF`
Install downloaded DEB: `sudo apt install -y ./docker-desktop.deb`

## Errors
### Install packages kept back
Command: `sudo apt upgrade`
Message:
```
The following packages have been kept back:
  qemu-block-extra qemu-system-common
```
Upgrade individually: `sudo apt upgrade -y qemu-block-extra qemu-system-common`
Install individually: `sudo apt install --only-upgrade qemu-block-extra qemu-system-common`
Install by full upgrade (NOT WORK): `sudo apt full-upgrade -y`
