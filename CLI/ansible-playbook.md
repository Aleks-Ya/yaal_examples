# ansible-playbook CLI

Execute a playbook on localhost: 
```
ansible-playbook -i "localhost," -c local playbook.yml
```
Verbose:
```
ansible-playbook -v ...
#Levels: -v, -vv, -vvv, -vvvv
```
Dry-run (check if current server configuration matches to the playbook):
```
ansible-playbook --check -i "localhost," -c local playbook.yml
```
Run as sudo:
```
sudo ansible-playbook playbook.yml
```
Check Playbook syntax:
```
ansible-playbook --syntax-check playbook.yml
```
Execute Playbook step-by-step (confirm each task):
```
ansible-playbook --step playbook.yml
```
Visualize Playbooks, Plays and Tasks in a Playbook:
```
ansible-playbook --list-tasks playbook.yml
```
