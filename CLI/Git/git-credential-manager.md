# Git Credential Manager CLI

## Install
1. Download DEB from https://github.com/git-ecosystem/git-credential-manager/releases
2. Install DEB: `sudo dpkg -i gcm-linux_amd64.2.6.0.deb`
3. Configure: `git-credential-manager configure`


Help: `git-credential-manager --help`
Store credential: `echo -e "protocol=https\nhost=hello.com\nusername=user1\npassword=pass1" | git-credential-manager store`

## Credential store
### Plain text
1. Set output file: `git config --global credential.plaintextStorePath /tmp/plain-text-credentials`
2. Choose store: `git config --global credential.credentialStore plaintext`
3. Store credentials: `echo -e "protocol=https\nhost=plain.com\nusername=user1\npassword=pass1" | git-credential-manager store`
4. See credentials: `cat /tmp/plain-text-credentials/git/https/plain.com/user1.credential`
5. Get credentials: `echo -e "protocol=https\nhost=plain.com" | git-credential-manager get`
6. Delete credentials: `echo -e "protocol=https\nhost=plain.com" | git-credential-manager erase`

### In-memory cache
1. Choose store: `git config --global credential.credentialStore cache`
2. Store credentials: `echo -e "protocol=https\nhost=memory.com\nusername=user2\npassword=pass2" | git-credential-manager store`
3. Get credentials: `echo -e "protocol=https\nhost=memory.com" | git-credential-manager get`
4. Clear cache: `git credential-cache exit`
5. Get credentials again (will be requested): `echo -e "protocol=https\nhost=memory.com" | git-credential-manager get`

### Secret service
1. Choose store: `git config --global credential.credentialStore secretservice`
2. Store credentials: `echo -e "protocol=https\nhost=secret.com\nusername=user3\npassword=pass3" | git-credential-manager store`
3. Get credentials: `echo -e "protocol=https\nhost=secret.com" | git-credential-manager get`
4. Get absent credentials: `echo -e "protocol=https\nhost=absent.com" | git-credential-manager get`
5. Get absent credentials again (still not stored): `echo -e "protocol=https\nhost=absent.com" | git-credential-manager get`
