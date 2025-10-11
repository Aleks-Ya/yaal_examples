# 030-backup-private-key

## Task
Export a private key and restore it back.

## Setup
1. Generate keys: `gpg --gen-key`
	1. Real name: `kata-backup-private-key`
	2. Email address: `kata-backup-private-key@email.com`
	3. Passphrase: `pass1`
2. Show the keys: 
	1. List public keys: `gpg --list-keys "kata-backup-private-key <kata-backup-private-key@email.com>"`
	2. List secret keys: `gpg --list-secret-keys "kata-backup-private-key <kata-backup-private-key@email.com>"`
3. Encrypt data:
	1. Encrypt: `echo "abc" | gpg --encrypt --armor -r kata-backup-private-key@email.com > /tmp/kata-backup-private-key.txt.asc`
	2. Review the cyphertext: `cat /tmp/kata-backup-private-key.txt.asc`
	3. Decrypt: `gpg -d /tmp/kata-backup-private-key.txt.asc`
3. Export the secret key:
	1. Export: `gpg --export-secret-keys --armor kata-backup-private-key@email.com > /tmp/kata-backup-private-key.asc`
	2. Review the backup content: `gpg --dry-run -q --import --import-options import-show /tmp/kata-backup-private-key.asc`
4. Delete the secret key: 
	1. Delete: `gpg --delete-secret-and-public-key kata-backup-private-key`
	2. Check: `gpg --list-secret-keys kata-backup-private-key`
	3. Decrypt fails: `gpg -d /tmp/kata-backup-private-key.txt.asc`
5. Restore public and secret keys from backup:
	1. Import: `gpg --import /tmp/kata-backup-private-key.asc`
	2. Verify: `gpg --list-secret-keys kata-backup-private-key`
	3. Decrypt succeeds: `gpg -d /tmp/kata-backup-private-key.txt.asc`

## Cleanup
1. Delete the backup: `rm /tmp/kata-backup-private-key.asc`
2. Delete the ciphertext: `rm /tmp/kata-backup-private-key.txt.asc`
3. Delete the key: `gpg --delete-secret-and-public-key kata-backup-private-key`
