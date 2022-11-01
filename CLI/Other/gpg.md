# gpg CLI

## Import
Show fingerprint of *.asc file (PGP digital signatures):
```
gpg --dry-run -q --import --import-options import-show VeraCrypt_PGP_public_key.asc
```
Import keys from *.asc file:
```
gpg --import VeraCrypt_PGP_public_key.asc
```
Import private key from a file:
```
gpg --import /tmp/CryptomatorVaultBackup-keys/CryptomatorVaultBackup-private.pgp
```

## Verify signature
Verify file with its signature: `gpg --verify veracrypt-1.24.deb.sig veracrypt-1.24.deb`

## Generate
Generate new key: `gpg --gen-key`

## List
List public keys (all): `gpg --list-keys`
List public keys (by substring from e.g. `John Dow <john@protonmail.com>`): `gpg --list-keys john@protonmail.com`
List secret keys: `gpg --list-secret-keys`

## Export
Show key details: `gpg  --export "John Dow <john@protonmail.com>" | hokey lint`
Export "CryptomatorVaultBackup" public key to a file:
```
gpg --output /tmp/public.pgp --armor --export CryptomatorVaultBackup
```
Export a private key (ask passhprase):
```
gpg --output /tmp/private.pgp --armor --export-secret-key CryptomatorVaultBackup
```
Export a private key (passhprase in command line):
```
gpg --output /tmp/private.pgp --armor --batch --pinentry-mode loopback --passphrase "my-pass-phrase" --export-secret-key CryptomatorVaultBackup
```
Check password for a private key: `gpg --export-secret-keys -a my-key-id > /dev/null && echo OK`

## Delete
Delete public and secret keys:`gpg --delete-secret-and-public-key CryptomatorVaultBackup`

## Other
Show version:`gpg --version`
Specify custom home directory: `gpg --homedir /tmp/gpg_home --list-keys`
Initialize empty home directory (will be initialized while listing keys): `gpg --homedir /tmp/gpg_home --list-keys`

## Encrypt/Decrypt file with password
### Individual file
#### Encrypt
With compression: `gpg -c my.txt` -> `my.txt.gpg`
Without compression: `gpg --compress-algo none -c my.txt`
Specify passpharase as a text: `gpg --batch --passphrase 12345 my.txt`
#### Decrypt
Decrypt: `gpg my.txt.gpg` -> `my.txt`
### All files in a folder

`find . -iname "*.txt" -exec gpg --batch --passphrase "12345" -c {} \;`

### Check file integrity
`gpg --decrypt --output /dev/null my.gpg`
