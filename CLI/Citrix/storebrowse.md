# storebrowse CLI

Location: `/opt/Citrix/ICAClient/util/storebrowse`

Help: `storebrowse --help`

`storebrowse -E vdinovartis.cloud.com`

## Errors
### Failed to load module "canberra-gtk-module"
Command: `/opt/Citrix/ICAClient/util/storebrowse -l`
Message: `Gtk-Message: 09:04:07.888: Failed to load module "canberra-gtk-module"`
Fix: `sudo apt install libcanberra-gtk-module libcanberra-gtk3-module`
