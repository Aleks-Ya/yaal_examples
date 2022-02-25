# ansible-vault CLI

Show version and config location: ansible-vault --version
View vault content: ansible-vault view foo.yml bar.yml baz.yml
Decrypt files: ansible-vault decrypt foo.yml bar.yml baz.yml
Create empty file: ansible-vault create foo.yml
Edit a file: ansible-vault edit foo.yml
Change password: ansible-vault rekey foo.yml bar.yml baz.yml
Encrypt files: ansible-vault encrypt foo.yml bar.yml baz.yml
