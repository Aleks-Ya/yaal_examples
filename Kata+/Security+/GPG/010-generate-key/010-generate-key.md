# 010-generate-key

## Task
Generate a GPG key and encrypt a text with it.

## Steps
1. Generate keys: `gpg --gen-key`
	1. Real name: `John`
	2. Email address:: `john@email.com`
	3. Passphrase: `john1`
2. Show the keys: 
	1. List public keys: `gpg --list-keys "John <john@email.com>"`
	2. List secret keys: `gpg --list-secret-keys "John <john@email.com>"`
3. Encrypt text
	1. Encrypt: `echo "abc" | gpg --encrypt --armor -r john@email.com`
	2. Decrypt: 
		1. Run `gpg --decrypt`
		2. Paste the encrypted message (including `-----BEGIN PGP MESSAGE-----` and `-----END PGP MESSAGE-----`)
		3. Press Ctrl-D
		4. Enter passphrase `john1`

## Cleanup
1. Delete keys: `gpg --delete-secret-and-public-key "John <john@email.com>"`

## History
- 2025-11-15 success
