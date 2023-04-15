# ufw CLI
Ubuntu Firewall

## Install
GUI: `sudo apt install -y gufw`

## Commands
Version: `sudo ufw version`
Show status: `sudo ufw status verbose`
Disable: `sudo ufw disable`
Enable: `sudo ufw enable`
Enabled logging: `sudo ufw logging on`
Disable logging: `sudo ufw logging off`
Allow Samba: `sudo ufw allow samba`
Disable Samba: `sudo ufw delete allow samba`
List rules (included in status): `sudo ufw status verbose`
