# useradd CLI

Create new user:
```bash
sudo useradd john
```
Create new user and home directory:
```bash
sudo useradd -m john
```
Create user and assign it to a group:
```bash
sudo useradd -g accounts john
```
Create user with specific home dir (and create it):
```bash
sudo useradd -b /tmp/home2 -m john
```
