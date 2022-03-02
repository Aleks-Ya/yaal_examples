# ansible-vault CLI

## Info
Show version and config location: `ansible-vault --version`

## Secret file
Encrypt files: `ansible-vault encrypt foo.yml bar.yml baz.yml`
View vault content: `ansible-vault view foo.yml bar.yml baz.yml`
Decrypt files: `ansible-vault decrypt foo.yml bar.yml baz.yml`
Create new file (open text editor): `ansible-vault create foo.yml`
Edit a file: `ansible-vault edit foo.yml`
Change password: `ansible-vault rekey foo.yml bar.yml baz.yml`

## Secret string
Encrypt a string: `ansible-vault encrypt_string 'abc'`
Encrypt a string with variable name: `ansible-vault encrypt_string "a1b2" --name 'key'`
Decrypt a string (password `123`):
```
echo '$ANSIBLE_VAULT;1.1;AES256
38346261616631326536643038633232633234326331373562613131303336396666643735363539
3464356537303761333137353038663934303136356363300a363835313663346164373938366638
66396364613532343331373466613030316538393834633936653633363665653765646261373838
6432383535376334330a626464336465623366636535363361363231346431303461643534343230
6338' |
ansible-vault decrypt
```
