# smbclient CLI

Install: `sudo apt install -y smbclient`

Test configuration file: `sudo testparm`

Help: `smbclient --help`
Set log level (0 - min logs, 10 - max logs): `smbclient -d 10`
Connect (with a Samba user `aleks`): `smbclient //172.16.129.98/hip -U aleks`
