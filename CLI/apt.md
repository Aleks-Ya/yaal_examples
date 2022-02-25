# apt CLI

apt

Search a package:
```
apt search my-package
```
List all version of a package:
```
apt list -a my-package
```
List history of installed packages:
```
grep "install " /var/log/dpkg.log
```
Remove unused packages:
```
sudo apt-get autoclean && sudo apt-get autoremove
```
List installed package:
```
apt list --installed
```

apt-key

List public keys:
```
apt-key list
```
