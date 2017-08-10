# Run Ansible against localhost

## Execute ad-hoc command against localhost:
ansible all -i "localhost," -c local -m shell -a 'echo hello world'

## Execute a playbook

### Without inventory
ansible-playbook -i "localhost," -c local localhost-playbook.yml

### With inventory
ansible-playbook -i hosts localhost-playbook.yml
