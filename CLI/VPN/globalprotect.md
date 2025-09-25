# globalprotect CLI

## Commands
Help: `man globalprotect`
Connect: `globalprotect connect --portal vpn-eu-nix.epam.com`
Disconnect: `globalprotect disconnect`
Reconnect: `globalprotect rediscover-network`
Status: `globalprotect show --status`
Version: `globalprotect show --version`
Statistics: `globalprotect show --statistics`
List gateways: `globalprotect show --gateway`

## Service
Status: `systemctl status gpd`
Stop: `sudo systemctl stop gpd`
Disable: `sudo systemctl disable gpd`
