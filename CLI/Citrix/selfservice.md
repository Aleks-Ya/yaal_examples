# selfservice CLI

Location: `/opt/Citrix/ICAClient/selfservice`

## Error
### Failed to load module "canberra-gtk-module"
Command: `/opt/Citrix/ICAClient/selfservice --icaroot /opt/Citrix/ICAClient`
Message: `Gtk-Message: 09:04:07.888: Failed to load module "canberra-gtk-module"`
Fix: `sudo apt install libcanberra-gtk-module libcanberra-gtk3-module`

### Driver does not support the 0x7dd1 PCI ID
Command: `/opt/Citrix/ICAClient/selfservice --icaroot /opt/Citrix/ICAClient`
Message:
```shell
MESA: warning: Driver does not support the 0x7dd1 PCI ID.
libEGL warning: egl: failed to create dri2 screen
```
Fix: set env var `LIBGL_ALWAYS_SOFTWARE=1 /opt/Citrix/ICAClient/selfservice --icaroot /opt/Citrix/ICAClient`

### Failed to open
Command: `LIBGL_ALWAYS_SOFTWARE=1 /opt/Citrix/ICAClient/selfservice --icaroot /opt/Citrix/ICAClient`
Message:
```shell
Failed to open /etc/systeminfo file
Failed to open /etc/tzagent/info file
Failed to open /setup/terminal.ini
```
Cause: missing files
Fix:
```shell
sudo mkdir -p /etc/tzagent /setup
sudo touch /etc/systeminfo /etc/tzagent/info /setup/terminal.ini
```
