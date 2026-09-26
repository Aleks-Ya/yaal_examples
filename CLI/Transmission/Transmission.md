# Transmission GUI

## Install

Access to ADATA:
```shell
sudo apt remove --purge transmission-gtk transmission-common
flatpak install flathub com.transmissionbt.Transmission
flatpak override --user --filesystem=/media/aleks/ADATA com.transmissionbt.Transmission
```

No access to ADATA: `sudo apt install -y transmission`
