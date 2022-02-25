duplicity CLI

Show help:
```
duplicity -h
```
Make a full backup of a dir:
```
PASSPHRASE="my-pass-phrase" duplicity full --encrypt-key CryptomatorVaultBackup /home/aleks/.local/share/Cryptomator/mnt/2Qa6QB8BnKFF_0 file:///tmp/CryptomatorVaultBackup
```
List files in a backup dir:
```
duplicity list-current-files file:///tmp/CryptomatorVaultBackup
```
Restore a backup to a dir:
```
PASSPHRASE="my-pass-phrase" duplicity file:///tmp/CryptomatorVaultBackup /tmp/CryptomatorVaultBackup-restore
```
Verify that a backup has no differences from the origin dir: 
```
PASSPHRASE="my-pass-phrase" duplicity verify --compare-data file:///tmp/CryptomatorVaultBackup /tmp/CryptomatorVault
```
Show saved versions (times):
```
duplicity collection-status file:///tmp/CryptomatorVaultBackup
```
