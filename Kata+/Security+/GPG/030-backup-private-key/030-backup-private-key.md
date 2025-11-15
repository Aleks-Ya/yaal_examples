# 030-backup-private-key

## Task
Export a private key and restore it back.

## Steps
1. Set environment variables
	```shell
	set -x
	export NAME=kata-backup-private-key
	export EMAIL=kata-email@tutanota.com
	export ENCRYPTED_FILE=/tmp/kata-backup-private-key.txt.asc
	export SECRET_KEY_FILE=/tmp/kata-backup-private-key.asc
	```
2. Generate keys:
	1. Generate key: `gpg --quick-generate-key "${NAME} <${EMAIL}>"`
	2. Passphrase: `pass1`
	3. Show the keys: 
		1. List public keys: `gpg --list-keys $NAME`
		2. List secret keys: `gpg --list-secret-keys $NAME`
3. Encrypt data:
	1. Encrypt: `echo "abc" | gpg --encrypt --armor -r $NAME > $ENCRYPTED_FILE`
	2. Review the cyphertext: `cat $ENCRYPTED_FILE`
	3. Decrypt: `gpg -d $ENCRYPTED_FILE`
4. Export the secret key:
	1. Export: `gpg --export-secret-keys --armor $NAME > $SECRET_KEY_FILE`
	2. Review the backup content: `gpg --dry-run -q --import --import-options import-show $SECRET_KEY_FILE`
5. Delete the secret key: 
	1. Delete: `gpg --delete-secret-and-public-key $NAME`
	2. Check: `gpg --list-secret-keys $NAME`
	3. Decrypt fails: `gpg -d $ENCRYPTED_FILE`
6. Restore public and secret keys from backup:
	1. Import: `gpg --import $SECRET_KEY_FILE`
	2. Verify: `gpg --list-secret-keys $NAME`
	3. Decrypt succeeds: `gpg -d $ENCRYPTED_FILE`

## Cleanup
1. Delete files: `rm $ENCRYPTED_FILE $SECRET_KEY_FILE`
2. Delete the key: `gpg --delete-secret-and-public-key $NAME`
3. Delete env variables: `set +x; unset NAME EMAIL ENCRYPTED_FILE SECRET_KEY_FILE`

## History
- 2025-11-15 success
