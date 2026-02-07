# 020-local-file-storage-backend

## Task
Backup to a Local File Storage Backend without encryption.

## Steps
1. Open a new terminal
2. Set environment variables
	```shell
	set -x
	export SOURCE_DIR=/tmp/kata-source-local-file-storage-backend
	export BACKUP_DIR=/tmp/kata-backup-local-file-storage-backend
	export RESTORE_DIR=/tmp/kata-restore-local-file-storage-backend
	```
3. Create empty dirs: `rm -rf $SOURCE_DIR $BACKUP_DIR $RESTORE_DIR && mkdir $SOURCE_DIR`
4. Create the source dir:
	1. Create a file: `echo abc > $SOURCE_DIR/data.txt`
	2. List files: `ll $SOURCE_DIR`
5. Backup: 
	1. Backup: `duplicity backup --no-encryption $SOURCE_DIR file://$BACKUP_DIR`
	2. List files: `ll $BACKUP_DIR` 
6. Restore:
	1. Verify NOT equal: `sha256sum $SOURCE_DIR/data.txt $RESTORE_DIR/data.txt`
	2. Restore: `duplicity restore --no-encryption file://$BACKUP_DIR $RESTORE_DIR`
	3. Verify equal: `sha256sum $SOURCE_DIR/data.txt $RESTORE_DIR/data.txt`

## Cleanup
1. Close the termimal

## History
- 2026-02-08 success
