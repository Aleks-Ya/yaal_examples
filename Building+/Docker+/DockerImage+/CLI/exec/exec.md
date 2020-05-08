# Use `docker exec` command

## Connect to a running container with Bash
```
# With default (?) user
docker exec -it container_name bash

# With specific user
docker exec -it -u root container_name bash
```
