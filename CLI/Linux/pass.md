# pass CLI

Install: `sudo apt install -y pass`
Storeage location: `~/.password-store`

## Commands
Help: `pass help`
Version: `pass version`

Initialize a password store: `pass init "pass-tool <pass@mail.com>"`
List passwords: `pass ls`
Add a password: `pass insert folder1/pass-name-1`
Show a password: `pass show folder1/pass-name-1`
Delete a password: `pass rm pass-name-1`
Edit a password: `pass edit pass-name-1`

## Generate password
Default length 25 symbols: `pass generate pass-name-1`
Specific lenght (12 symbols): `pass generate pass-name-1 12`
Without special symbols: `pass generate -n pass-name-1`
Don't display new password: `pass generate pass-name-1 > /dev/null`
