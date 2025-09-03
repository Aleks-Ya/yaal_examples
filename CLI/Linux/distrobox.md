# distrobox CLI

Site: https://distrobox.it
GitHub: https://github.com/89luca89/distrobox

Install: `sudo apt install distrobox`

Help: `distrobox help`
List containers: `distrobox ls`
Create a container: `distrobox create --name hello-alpine --image alpine`
Enter a container: `distrobox enter hello-alpine`
Exit a container: `exit`

## Images
Ubuntu 22.04: `distrobox create --name ubuntu2204 --image ubuntu:22.04`
Alpine Linux: `distrobox create --name hello-alpine --image alpine`
