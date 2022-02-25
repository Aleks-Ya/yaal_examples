# useradd CLI

Create new user:
```
sudo useradd john
```
Create new user and home directory:
```
sudo useradd -m john
```
Create user and assign it to a group:
```
sudo useradd -g accounts john
```
Create user with specific home dir (and create it):
```
sudo useradd -b /tmp/home2 -m john
```
