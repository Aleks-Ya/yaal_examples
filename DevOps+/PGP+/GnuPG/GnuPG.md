# GnuPG

## Empty tmp home dir
`rm -rf ./gnupg_tmp && mkdir ./gnupg_tmp`

## Set right permissions to the home dir
`chmod -R go-rwx ./gnupg_tmp`

## Initialize empty home dir
`gpg --homedir ./gnupg_tmp --list-keys`

## Create the master key
`gpg --homedir ./gnupg_tmp --gen-key`

Parameters: 
- Real name: `John Smith`
- Email address: `smith@mail.com`
- Passphrase: `pass1`

Generated `User ID` is `John Smith <smith@mail.com>`.

## List keys in the home dir
1. Public keys: `gpg --homedir ./gnupg_tmp --list-keys`
1. Secret keys: `gpg --homedir ./gnupg_tmp --list-secret-keys`

## Search keys in a keyserver
Search VeraCrypt keys:
`gpg --homedir ./gnupg_tmp --keyserver keys.gnupg.net --search-keys veracrypt@idrix.fr`
`gpg --homedir ./gnupg_tmp --keyserver pool.sks-keyservers.net --search-keys veracrypt@idrix.fr`
Search Tails keys:
`gpg --homedir ./gnupg_tmp --keyserver pool.sks-keyservers.net --search-keys "Tails developers <tails@boum.org>"`
`gpg --homedir ./gnupg_tmp --keyserver pool.sks-keyservers.net --search-keys FE029CB4AAD4788E1D7828E8A8B0F4E45B1B50E2`


## Import another user's public key 
### From a file
Import VeraCrypt public key from a file:
1. Download public key: `curl -o /tmp/VeraCrypt.asc https://www.idrix.fr/VeraCrypt/VeraCrypt_PGP_public_key.asc`
2. Import public key: `gpg --homedir ./gnupg_tmp --import /tmp/VeraCrypt.asc`
3. List keys: `gpg --homedir ./gnupg_tmp --list-keys`

### From a key server
Import Tails key: 
`gpg --homedir ./gnupg_tmp --keyserver pool.sks-keyservers.net --recv-keys FE029CB4AAD4788E1D7828E8A8B0F4E45B1B50E2`  

## Encrypt document with the master key
`gpg --homedir ./gnupg_tmp --sign --trust-model always --armor -r FE029CB4AAD4788E1D7828E8A8B0F4E45B1B50E2 message.txt`
