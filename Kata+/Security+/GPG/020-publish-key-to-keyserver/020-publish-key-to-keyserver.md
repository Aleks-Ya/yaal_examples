# 020-publish-key-to-keyserver

## Task
Generate a public key and publish it to a public Key Server.

## Setup
1. Generate keys: `gpg --gen-key`
	1. Real name: `kata-publish-key-to-keyserver`
	2. Email address: `kata-publish-key-to-keyserver@email.com`
	3. Passphrase: `pass1`
2. Publish the public key to the Key Server
	1. Public key: `gpg --keyserver keys.openpgp.org --send-keys CA925CD6C9E8D064FF05B4728190C4130ABA0F98`
	2. Retrive the key: `gpg --keyserver keys.openpgp.org --recv-keys CA925CD6C9E8D064FF05B4728190C4130ABA0F98`

## Cleanup
1. Delete local key: `gpg --delete-secret-and-public-keys kata-publish-key-to-keyserver`
2. Key deletion from Key Server is not possible.
