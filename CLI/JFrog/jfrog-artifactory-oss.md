# jfrog-artifactory-oss

## Install
Install: 
1. Install package: `sudo apt install jfrog-artifactory-oss`
2. Set `true` in line `allowNonPostgresql: false` in `/opt/jfrog/artifactory/var/etc/system.yaml`
3. Start service: `sudo systemctl start artifactory.service`
4. Open http://localhost:8082/ui/
5. Default credentials: `admin`/`password`
6. New password: `_Password111`

Folders:
- Installation: `/opt/jfrog/artifactory`
- Logs: `/opt/jfrog/artifactory/var/log`
- Configuration: `/opt/jfrog/artifactory/var/etc/system.yaml`
- Configuration templates: `/opt/jfrog/artifactory/var/etc`

## Service
Status: `systemctl status artifactory.service`
Start: `sudo systemctl start artifactory.service`
Stop: `sudo systemctl stop artifactory.service`
Disable: `sudo systemctl disable artifactory.service`
Tail log: `journalctl -f -u artifactory.service`
