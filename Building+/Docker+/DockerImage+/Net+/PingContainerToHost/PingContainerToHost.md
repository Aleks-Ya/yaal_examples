# Connect from container to a service ran on host

## Network `host`
1. Run a test server on host: `python -m http.server 1091`
2. Test connection from container: `docker run --rm --network host alpine-updated nc -zv localhost 1091`

## Network `bridge` (default)
1. Run a test server on host: `python -m http.server 1091`
2. Enable port in firewall: `sudo ufw allow 1091/tcp`
3. Find host IP: `export HOST_IP=$(docker network inspect bridge --format='{{(index .IPAM.Config 0).Gateway}}')`
4. Test container: `docker run -it --rm alpine-updated curl http://${HOST_IP}:1091`
