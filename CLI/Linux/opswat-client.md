# opswat-client CLI

## Install
Remove the client: `sudo dpkg -r opswatclient`

## Commands
Help: `opswat-client -h`
Show found problems: `opswat-client -r`

## Service
Status: `sudo systemctl status opswatclient`
Stop: `sudo systemctl stop opswatclient`
Start: `sudo systemctl start  opswatclient`
Process name (choose `All Processes` in `System Monitor`): `daxz`
Show logs and follow: `journalctl -u opswatclient -f`
Disable: `sudo systemctl disable opswatclient`

## Errors
### `OPSWAT status: Not Found`
Message: `Your device needs to be validated via OPSWAT MetaAccess, however we couldn't detect the OPSWAT agent on your device.`
Reason: some OPSWAT JS are blocked in the country (visible in Network debugger)
Solution: use VPN (e.g. `nordvpn connect Denmark`)
