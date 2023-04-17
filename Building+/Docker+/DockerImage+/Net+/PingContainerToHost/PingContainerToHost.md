# Connect from container to a service ran on host

## Option 1: network `host`
1. Run a test server on host: `python -m http.server 1091`
2. Test connection from container: `docker run --rm --network host alpine nc -zv localhost 1091`

## (DO NOT WORK) Option 2: network `bridge` (default)
1. Run a test server on host: `python -m http.server 1091`
2. Run container: `docker run -it --rm --name ping alpine`
3. Test connection from container: `nc -zv localhost 1091`
