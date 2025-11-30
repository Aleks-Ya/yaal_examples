# 020-encrypt-data-key

## Task
Encrypt and decrypt data using a Data Key.

## Steps
1. Create a Key
	1. Key type: `Symmetric`
	2. Key usage: `Encrypt and decrypt`
	3. Key material origin: `KMS`
	4. Regionality: `Single-Region key`
	5. Alias: `kms-key-1`
	6. Key administrators: `Admin`
	7. Key users: `Admin`
2. Encrypt data
	1. Remove temp files: `rm -f /tmp/encrypted.txt /tmp/encrypted_data_key`
	2. Generate a Data Key: `DATA_KEY=$(aws kms generate-data-key --key-id alias/kms-key-1 --key-spec AES_256 --output json)`
	3. Store the encrypted Data Key: `echo $DATA_KEY | jq -r .CiphertextBlob > /tmp/encrypted_data_key.txt`
	4. Encrypt data: `echo -n "My Data" | openssl enc -e -aes256 -pbkdf2 -k $(echo $DATA_KEY | jq -r .Plaintext) -base64 > /tmp/encrypted.txt`
	5. Destroy the plaintext Data Key: `unset DATA_KEY`
3. Decrypt data
	1. Decrypt the Data Key: `PLAINTEXT_DATA_KEY=$(aws kms decrypt --output text --ciphertext-blob file:///tmp/encrypted_data_key.txt --query Plaintext)`
	2. Decrypt the data: `cat /tmp/encrypted.txt | openssl enc -d -aes256 -pbkdf2 -k $PLAINTEXT_DATA_KEY -base64`
	3. Destroy the plaintext Data Key: `unset PLAINTEXT_DATA_KEY`

## Cleanup
1. Delete the Key (waiting ≥7 days)

## History
