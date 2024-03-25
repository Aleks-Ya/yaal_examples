# AlmaLinux Docker image

## Standard image
Sources: https://hub.docker.com/_/almalinux
Run a command:`docker run --rm --name almalinux almalinux echo 'Hello, World!'`
Run Bash:`docker run -it --rm --name almalinux almalinux bash`

## Updated image
1. Fetch the last version: `docker pull almalinux`
2. Build: `docker build -t almalinux-updated AlmaLinuxUpdated`
3. Check: `docker run -it --rm --name almalinux-updated almalinux-updated which telnet`
4. Run: `docker run -it --rm --name almalinux-updated almalinux-updated`
