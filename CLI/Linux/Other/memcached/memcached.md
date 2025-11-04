# memcached

## Install
APT: `sudo apt install memcached libmemcached-tools`

## Service
Status: `systemctl status memcached`
Disable: `sudo systemctl disable memcached`
Stop: `sudo systemctl stop memcached`

## Commands
Help: `memccp --help`
Show statistics: `memcstat --servers=localhost`
List all keys: `memcdump --servers=localhost`
Get a value: `memccat --servers=localhost my_key`
