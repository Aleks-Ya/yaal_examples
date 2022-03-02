# ansible-galaxy CLI

Create a role template in current dir:
```
ansible-galaxy role init my_role
```
Check if an Ansible collection "community.general" installed:
```
ansible-galaxy collection list | grep community.general
```
Install third-party collection or role:
```
ansible-galaxy install comcast.sdkman
```
List Ansible roles:
```
ansible-galaxy role list
```
