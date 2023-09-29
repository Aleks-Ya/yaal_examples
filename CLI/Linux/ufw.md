# ufw CLI
Ubuntu Firewall

## Install
GUI: `sudo apt install -y gufw`

## Commands
### Info
Version: `sudo ufw version`
Show status: `sudo ufw status verbose`

### Enable/Disable
Enable: `sudo ufw enable`
Disable: `sudo ufw disable`

### Rules
Allow Samba: `sudo ufw allow samba`
Disable Samba: `sudo ufw delete allow samba`
List rules (included in status): `sudo ufw status verbose`

### Logging
See logs: `less /var/log/ufw.log`
Enabled logging: `sudo ufw logging on`
Disable logging: `sudo ufw logging off`
Check is loggin on (included in status): `sudo ufw status verbose`

## Enable port
Allow port: `sudo ufw allow 7077`
Cancel allow port: `sudo ufw delete allow 7077`
Enable port 1093: `sudo ufw allow 1093/tcp`
Cancel enabling port 1093: `sudo ufw delete allow 1093/tcp`
