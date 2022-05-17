# duplicity CLI

## Info
Show version: `duplicity --version`
Show help: `duplicity -h`
Log level (0-9): `duplicity -v 9 ...`
Specify passphrase: `PASSPHRASE="my-pass-phrase" duplicity file:///tmp/data /tmp/data-restore`

## Backup
Make a full backup of a dir: `duplicity full --encrypt-key john.dow@mail.com /tmp/data file:///tmp/backup`
List files in a backup dir: `duplicity list-current-files file:///tmp/backup`
Restore a backup to a dir: `duplicity file:///tmp/backup /tmp/restore`
Verify that a backup has no differences from the origin dir:  `duplicity verify --compare-data file:///tmp/backup /tmp/data`
Show saved versions (times): `duplicity collection-status file:///tmp/backup`

## Cleanup
List files to cleanup (not delete): `duplicity cleanup file:///home/aleks/yandex-disk/backup/CryptomatorVaultBackup`