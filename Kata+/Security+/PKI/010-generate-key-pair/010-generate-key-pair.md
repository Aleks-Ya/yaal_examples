# 010-generate-key-pair

## Task
Generate a key pair and encrypt/decript a message with it.

## Setup
1. Create an empty dir: `mkdir /tmp/kata-010-generate-key-pair && cd /tmp/kata-010-generate-key-pair`
2. Generate a private key: 
	1. Generate: `openssl genrsa -out private.pem`
	2. Review: `openssl rsa -text -in private.pem`
3. Export the public key: 
	1. Export: `openssl rsa -in private.pem -pubout > public.pem`
	2. Review: `openssl rsa -text -pubin -in public.pem`
4. Use the key pair:
	1. Create original file: `echo "My Text" > original.txt`
	2. Encrypt: `openssl pkeyutl -encrypt -pubin -inkey public.pem -in original.txt -out encrypted.bin`
	3. Decrypt: `openssl pkeyutl -decrypt -inkey private.pem -in encrypted.bin -out decrypted.txt`
	4. Assert: `cat decrypted.txt`

## Cleanup
1. Delete dir: `rm -rf /tmp/kata-010-generate-key-pair`
