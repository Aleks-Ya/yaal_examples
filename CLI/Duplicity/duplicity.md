# duplicity CLI
Version should be 2.x.x or later.

Install: 
- `sudo snap install duplicity` (latest)
- `sudo apt install duplicity` (outdated)

## Info
Show version: `duplicity --version`
Show help: `duplicity -h`
Log level (0-9): `duplicity -v 9 ...` or whithout space `duplicity -v9 ...`
Specify passphrase: `PASSPHRASE="my-pass-phrase" duplicity file:///tmp/data /tmp/data-restore`
Show progress: `duplicity full --progress --no-encryption /tmp/data file:///tmp/backup`

## Backup with encryption
Make a full backup of a dir: `duplicity full --encrypt-key john.dow@mail.com /tmp/data file:///tmp/backup`
Make an increment backup of a dir: `duplicity incremental --encrypt-key john.dow@mail.com /tmp/data file:///tmp/backup`
List saved times: `duplicity collection-status file:///tmp/backup`
List files in a backup dir: `duplicity list-current-files file:///tmp/backup`
Restore a backup to a dir: `duplicity restore file:///tmp/backup /tmp/restore`
Verify that a backup has no differences from the origin dir (with data comparison):  `duplicity verify --compare-data file:///tmp/backup /tmp/data`
Verify that a backup has no differences from the origin dir (without data comparison):  `duplicity verify file:///tmp/backup /tmp/data`
Show saved versions (times): `duplicity collection-status file:///tmp/backup`

## Backup without encryption
Make a full backup of a dir: `duplicity full --no-encryption /tmp/data file:///tmp/backup`
Make an increment backup of a dir: `duplicity incremental --no-encryption /tmp/data file:///tmp/backup`
List saved times: `duplicity collection-status file:///tmp/backup`
Restore a backup to a dir (latest version): `duplicity restore --no-encryption file:///tmp/backup /tmp/restore`
Restore a backup to a dir (specific time): `duplicity restore --no-encryption -t "2023-01-15T22:51:05" file:///tmp/backup /tmp/restore`
Verify that a backup has no differences from the origin dir (with data comparison):  `duplicity verify --no-encryption --compare-data file:///tmp/backup /tmp/data`
Verify that a backup has no differences from the origin dir (without data comparison):  `duplicity verify --no-encryption file:///tmp/backup /tmp/data`

## Cleanup
List files to cleanup (not delete): `duplicity cleanup file:///home/aleks/yandex-disk/backup/CryptomatorVaultBackup`
