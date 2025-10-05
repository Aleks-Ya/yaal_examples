# 010-encrypt-kms-key

## Task
Create a KMS key. Encrypt and decrypt a data using this key.

## Setup
1. Create a Key
	1. Key type: `Symmetric`
	2. Key usage: `Encrypt and decrypt`
	3. Key material origin: `KMS`
	4. Regionality: `Single-Region key`
	5. Alias: `kms-key-1`
	6. Key administrators: `Admin`
	7. Key users: `Admin`
2. Encrypt data
	1. Encrypt: `aws kms encrypt --output text --key-id alias/kms-key-1 --plaintext $(echo "My secret data" | base64) --query CiphertextBlob > /tmp/ciphertext.txt`
3. Decrypt data
	1. Decrypt: `aws kms decrypt --output text --ciphertext-blob file:///tmp/ciphertext.txt --query Plaintext | base64 -d`

## Cleanup
1. Delete the Key (waiting ≥7 days)
