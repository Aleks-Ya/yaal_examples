# Caddy

Site: https://caddyserver.com
Docs: https://caddyserver.com/docs/
GitHub: https://github.com/caddyserver/caddy-docker
DocerHub: https://hub.docker.com/_/caddy
Dockerfile: https://github.com/caddyserver/caddy-docker/blob/master/2.10/alpine/Dockerfile

Run test server: 
1. Run: `docker run --rm -p 80:80 caddy caddy respond -l :80 -s 201 -b "Hello, Caddy" -H "Header1: Value1"`
2. Test: `curl -i localhost`
