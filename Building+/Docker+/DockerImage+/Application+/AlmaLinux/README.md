# AlmaLinux Docker image

## Standard image
Sources: https://hub.docker.com/_/almalinux
Run a command:`docker run --rm --name almalinux almalinux echo 'Hello, World!'`
Run Bash:`docker run -it --rm --name almalinux almalinux bash`

## Updated image
Build: `docker build -t almalinux-updated AlmaLinuxUpdated`
Run: `docker run -it --rm --name almalinux-updated almalinux-updated`
Check: `docker run -it --rm --name almalinux-updated almalinux-updated which telnet`
