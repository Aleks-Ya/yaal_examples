# GnuPG

## Empty tmp home dir
`rm -rf ./gnupg_tmp && mkdir ./gnupg_tmp`

## Set right permissions to the home dir
`chmod -R go-rwx ./gnupg_tmp`

## Initialize empty home dir
`gpg --homedir ./gnupg_tmp --list-keys`

## Create primary key
`gpg --homedir ./gnupg_tmp --gen-key`

Parameters: 
- Real name: `John Smith`
- Email address: `smith@mail.com`
- Passphrase: `pass1`

Generated `User ID` is `John Smith <smith@mail.com>`.

## List keys in the home dir
1. Public keys: `gpg --homedir ./gnupg_tmp --list-keys`
1. Secret keys: `gpg --homedir ./gnupg_tmp --list-secret-keys`

## Import another user's key 
1. Download public key: `curl -o /tmp/VeraCrypt.asc https://www.idrix.fr/VeraCrypt/VeraCrypt_PGP_public_key.asc`
2. Import public key: `gpg --homedir ./gnupg_tmp --import /tmp/VeraCrypt.asc`
3. List keys: `gpg --homedir ./gnupg_tmp --list-keys`