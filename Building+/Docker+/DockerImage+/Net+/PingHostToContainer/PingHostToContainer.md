# Connect from host to container

## Network `host`
*Works on Linux. Do NOT work on Windows.*
1. Run server in container: `docker run --rm --network host python python -m http.server 1091`
2. Test connection from host:
	1. From terminal:  `nc -zv localhost 1091`
	2. From browser: `http://localhost:1091`

## Network `bridge` (default)
*Works both on Linux and Windows*
1. Run server in container: `docker run --rm -p 1091:1091 python python -m http.server 1091`
2. Test connection from host:
	1. From terminal:  `nc -zv localhost 1091`
	2. From browser: `http://localhost:1091`
