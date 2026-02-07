# 010-generate-key

## Task
Generate a GPG key and encrypt a text with it.

## Steps
1. Open a new terminal
2. Set environment variables
	```shell
	set -x
	export KEY_ID="John <john@email.com>"
	```
3. Generate keys: `gpg --gen-key`
	1. Real name: `John`
	2. Email address:: `john@email.com`
	3. Passphrase: `john1`
4. Show the keys: 
	1. List public keys: `gpg --list-keys "$KEY_ID"`
	2. List secret keys: `gpg --list-secret-keys "$KEY_ID"`
5. Encrypt text
	1. Encrypt: `echo abc | gpg --encrypt --armor -r "$KEY_ID"`
	2. Decrypt: 
		1. Run `gpg --decrypt`
		2. Paste the encrypted message (including `-----BEGIN PGP MESSAGE-----` and `-----END PGP MESSAGE-----`)
		3. Press Ctrl-D
		4. Enter passphrase `john1`
		5. Expected text: `abc`

## Cleanup
1. Delete keys: `gpg --delete-secret-and-public-key "$KEY_ID"`

## History
- 2025-11-15 success
- 2026-02-08 success
