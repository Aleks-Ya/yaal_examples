# "Ansible Control Machine" is current OS, "Ansible Remote Machine" is Docker container

## Build Docker image
`docker build -t ansible-remote-machine-in-docker:1 .`

## Run Docker image
`docker run --rm -it --name ansible-remote-machine-in-docker -p 52023:22 ansible-remote-machine-in-docker:1`

## Connect via SSH
### Prepare
Get server IP: `export CONTAINER_IP=$(docker exec ansible-remote-machine-in-docker hostname -I)`
Add server fingerprint to kown_hosts: `ssh-keyscan $CONTAINER_IP >> ~/.ssh/known_hosts`
Copy public key to server: `sshpass -p 'screencast' ssh-copy-id -o StrictHostKeyChecking=no root@$CONTAINER_IP`
Check connection: `ssh root@$CONTAINER_IP`

## Run Ansible

### Execute ad-hoc command against localhost
`ansible all -i "$CONTAINER_IP," -c local -m shell -a 'echo hello world'`

### Execute a playbook
Without inventory: `ansible-playbook -i "$CONTAINER_IP," -c local playbook.yml`
With inventory: `ansible-playbook -i hosts playbook.yml`
