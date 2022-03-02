# Ansible Examples
Examples of using Ansible's modules.

## Run an example
`ansible-playbook playbook/variable/scopes/playbook.yml`

## Logging
Set verbose level (`-v`, `-vv`, `-vvv`):
`ansible-playbook -vvv playbook/variable/scopes/playbook.yml`

## Vault
Password file: `.vault_pass_file`
Encrypt file: `ansible-vault encrypt ./vault/database_password.txt`
View encrypted file: `ansible-vault view ./vault/database_password.txt`
Decrypt file: `ansible-vault decrypt ./vault/database_password.txt`
Encrypt string as a property: `ansible-vault encrypt_string --name 'key' 'seckey'`