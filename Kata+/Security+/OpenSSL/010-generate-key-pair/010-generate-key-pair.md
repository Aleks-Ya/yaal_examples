# 010-generate-key-pair

## Task
Generate a key pair and encrypt/decript a message with it.

## Steps
1. Open a new terminal
2. Set environment variables
	```shell
	set -x
	export DIR=/tmp/kata-dir-generate-key-pair
	```
3. Create an empty dir: `rm -rf $DIR && mkdir $DIR && cd $DIR`
4. Generate a private key: 
	1. Generate: `openssl genrsa -out private.pem`
	2. Review: `openssl rsa -text -in private.pem`
5. Export the public key: 
	1. Export: `openssl rsa -in private.pem -pubout > public.pem`
	2. Review: `openssl rsa -text -pubin -in public.pem`
6. Use the key pair:
	1. Create original file: `echo "My Text" > original.txt`
	2. Encrypt: `openssl pkeyutl -encrypt -pubin -inkey public.pem -in original.txt -out encrypted.bin`
	3. Decrypt: `openssl pkeyutl -decrypt -inkey private.pem -in encrypted.bin -out decrypted.txt`
	4. Assert: `cat decrypted.txt`

## Cleanup
1. Delete dir: `rm -rf $DIR`
2. Close the terminal

## History
- 2026-02-08 success
