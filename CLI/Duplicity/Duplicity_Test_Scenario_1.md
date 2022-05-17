# Duplicity Test Scenario 1
Duplicity backups a directory with the primary key.


1. Cleanup
`rm -rf /tmp/d1 && mkdir -p /tmp/d1`
2. Create new primary keys
`gpg --homedir /tmp/d1/.gnupg --gen-key`
Real name: `John Dow`
Email address: `john.dow@mail.com`
Passphrase: `jd123`


NOT FINISHED

3. Create test source directory
4. Backup
5. Restore
6. Compare restored files