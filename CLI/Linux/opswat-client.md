# opswat-client CLI

## Install
Remove the client: `sudo dpkg -r opswatclient`

## Commands
Help: `opswat-client -h`
Show found problems: `opswat-client -r`

## Service
Stop: `sudo service opswatclient stop`
Start: `sudo service opswatclient start`
Process name: `daxz`

## Errors
### `OPSWAT status: Not Found`
Message: `Your device needs to be validated via OPSWAT MetaAccess, however we couldn't detect the OPSWAT agent on your device.`
Reason: some OPSWAT JS are blocked in the country (visible in Network debugger)
Solution: use VPN (e.g. `nordvpn connect Denmark`)
