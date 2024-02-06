# pass CLI

Install: `sudo apt install -y pass`
Storage location: `~/.password-store`

## Commands

### Help
Help: `pass help`
Version: `pass version`

### Initialize
1. Generate new keypair: `gpg --gen-key`
	1. Realm Name: `Alex-Ya`
	2. Email: `ya_al@bk.ru`
	3. Leave passwords empty
2. Initialize a password store: `pass init "Alex-Ya <ya_al@bk.ru>"`
3. Check: 
	1. Generate a password: `pass generate pass-name-1`
	2. Delete the generated password: `pass rm pass-name-1`

### Manage passwords
List passwords: `pass` = `pass ls`
Add a password: `pass insert folder1/pass-name-1`
Show a password: `pass show folder1/pass-name-1`
Delete a password: `pass rm pass-name-1`
Edit a password: `pass edit pass-name-1`

### Generate password
Default length 25 symbols: `pass generate pass-name-1`
Specific lenght (12 symbols): `pass generate pass-name-1 12`
Without special symbols: `pass generate -n pass-name-1`
Don't display new password: `pass generate pass-name-1 > /dev/null`
