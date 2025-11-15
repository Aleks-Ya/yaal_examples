# 020-publish-key-to-keyserver

## Task
Generate a public key and publish it to a public Key Server.

## Steps
1. Set environment variables
	```shell
	set -x
	export NAME=kata-publish-key-to-keyserver
	export EMAIL=kata-email@tutanota.com
	```
2. Generate key:
	1. Generate key: `gpg --quick-generate-key "${NAME} <${EMAIL}>"`
	2. Passphrase: `pass1`
	3. Get key ID: `export ID=$(gpg --list-keys --with-colons $NAME | awk -F: '/^pub/ {print $5}')`
3. Publish the public key to the Key Server
	1. Public key: `gpg --keyserver keys.openpgp.org --send-keys $ID`
	2. Verify email: 
		1. Click the link sent to `kata-email@tutanota.com`
		2. Click button "Send Verification Email"
		3. Click another link sent to `kata-email@tutanota.com`
		4. Show the key: https://keys.openpgp.org/search?q=kata-email@tutanota.com
4. Retrieve the key:
	1. Delete local key: `gpg --delete-secret-and-public-keys $NAME`
	2. Show no key: `gpg --list-keys $NAME`
	2. Retrive the key: `gpg --keyserver keys.openpgp.org --recv-keys $ID`
	3. Show the key: `gpg --list-keys $NAME`

## Cleanup
1. Delete local key: `gpg --delete-secret-and-public-keys $NAME`
2. Unpublish the key in the Key Server:
	1. Open page https://keys.openpgp.org/manage
	2. Enter email `kata-email@tutanota.com`
	3. Open the link sent to email
	4. Click "Delete" button
	5. Verify: https://keys.openpgp.org/search?q=kata-email@tutanota.com
3. Delete env variables: `set +x; unset NAME EMAIL ID`

## History
- 2025-11-15 success
