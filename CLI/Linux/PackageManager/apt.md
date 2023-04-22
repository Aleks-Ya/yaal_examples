# apt CLI

## apt
Search a package: `apt search my-package`
List all version of a package: `apt list -a my-package`
List history of installed packages: `grep "install " /var/log/dpkg.log`
Remove unused packages: `sudo apt-get autoclean && sudo apt-get autoremove`
List installed package: `apt list --installed`
Show package details: `apt show firefox`
Export a key to a file: `sudo apt-key export EE2C95AB58DC2B0138D16B4FEFC4571D7C90E5AF > yandex_disk.gpg`
Delete a key: `sudo apt-key del EE2C95AB58DC2B0138D16B4FEFC4571D7C90E5AF`

## apt-key
List public keys: `apt-key list`
