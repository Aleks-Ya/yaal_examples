# gpg CLI

Default home directory: `~/.gnupg`

## Info
Show version:`gpg --version`
Help: `gpg --help`

## Import
Show fingerprint of `*.asc` file (PGP digital signatures): `gpg --dry-run -q --import --import-options import-show VeraCrypt_PGP_public_key.asc`
Import keys from `*.asc` file: `gpg --import VeraCrypt_PGP_public_key.asc`
Import private key from a file: `gpg --import /tmp/CryptomatorVaultBackup-keys/CryptomatorVaultBackup-private.pgp`

## Verify signature
Verify file with its signature: `gpg --verify veracrypt-1.24.deb.sig veracrypt-1.24.deb`

## Generate
Generate new key (interactive): `gpg --gen-key`
Generate new key (silent): `gpg --quick-generate-key "Your Name <your.email@example.com>"`

## List
List public keys (all): `gpg --list-keys`
List public keys (by substring from e.g. `John Dow <john@protonmail.com>`): `gpg --list-keys john@protonmail.com`
List secret keys (from `/home/aleks/.gnupg/private-keys-v1.d` folder): `gpg --list-secret-keys`
List keys from specific key ring: `gpg --keyring /home/aleks/.gnupg/pubring.kbx --list-keys`
List secret keys with KeyGrips: `gpg --list-secret-keys --with-keygrip`
List secret keys with sub-keys: `gpg --list-secret-keys --with-subkey-fingerprints`

## Export
Show key details: `gpg  --export "John Dow <john@protonmail.com>" | hokey lint`
Export `CryptomatorVaultBackup` public key to a file: `gpg --output /tmp/public.pgp -a --export CryptomatorVaultBackup`
Export a private key (ask passhprase): `gpg --output /tmp/private.pgp -a --export-secret-key CryptomatorVaultBackup`
Export a private key (passhprase in command line):
```shell
gpg --output /tmp/private.pgp -a --batch --pinentry-mode loopback --passphrase "my-pass-phrase" --export-secret-key CryptomatorVaultBackup
```
Check password for a private key: `gpg --export-secret-keys -a my-key-id > /dev/null && echo OK`

## Delete
Delete a public key: `gpg --delete-key "User ID"`
Delete a secret key: `gpg --delete-secret-key "john.doe@example.com"`
Delete public and secret keys:`gpg --delete-secret-and-public-key CryptomatorVaultBackup`

## Home directory
Specify custom home directory: `gpg --homedir /tmp/gpg_home --list-keys`
Initialize empty home directory (will be initialized while listing keys): `gpg --homedir /tmp/gpg_home --list-keys`

## Encrypt/Decrypt file with password
### Individual file
#### Encrypt
With compression: `gpg -c my.txt` -> `my.txt.gpg`
Without compression: `gpg --compress-algo none -c my.txt`
Specify output file: `gpg -c -o out.txt.gpg my.txt`
Specify passpharase as a text: `gpg --batch --passphrase 12345 my.txt`
#### Decrypt
Decrypt: `gpg -d my.txt.gpg` -> `my.txt`
Specify output file: `gpg -d -o out.txt my.txt.gpg`
### All files in a folder
`find . -iname "*.txt" -exec gpg --batch --passphrase "12345" -c {} \;`

### Check file integrity
1. If it doens't ask for passphrase: `echo RELOADAGENT | gpg-connect-agent`
2. `gpg -d --output /dev/null my.gpg`

## Encrypt/decrypt string
Encrypt: `echo abc | gpg -e -a -r john@protonmail.com`
Decrypt: 
	1. Run `gpg -d`
	2. Paste the encrypted message (including `-----BEGIN PGP MESSAGE-----` and `-----END PGP MESSAGE-----`)
	3. Press Ctrl-D
	4. Enter passphrase
Encrypt and decrypt: `echo abc | gpg -e -a -r john@protonmail.com | gpg -d`

## KeyServer
Import a public key from a Key Server: `gpg --keyserver keyserver.ubuntu.com --recv-keys 0xDEADBEEF`
Public a public key to a Key Server: `gpg --keyserver keyserver.ubuntu.com --send-keys CA925CD6C9E8D064FF05B4728190C4130ABA0F98`
