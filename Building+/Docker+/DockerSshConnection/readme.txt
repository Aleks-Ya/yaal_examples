A Docker container ready to SSH connection.

Build Docker image:
docker build -t docker-ssh-connection:1 .

Run Docker image:
docker run -p 52022:22 -tid --name ssh docker-ssh-connection:1
docker exec -it ssh bash
docker stop ssh
docker rm ssh

Connect via SSH:
Command "sshpass -p 'screencast'" sets the root's password.
Command "-o StrictHostKeyChecking=no" adds the container to known_hosts.
sshpass -p 'screencast' ssh -o StrictHostKeyChecking=no -p 52022 root@localhost
