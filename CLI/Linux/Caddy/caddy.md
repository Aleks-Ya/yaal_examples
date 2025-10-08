# caddy CLI

Site: https://caddyserver.com
Docs: https://caddyserver.com/docs/
GitHub: https://github.com/caddyserver/caddy

Install: `brew install caddy`
Docker: `Building+/Docker+/DockerImage+/Application+/Caddy`

## Commands

### Info
Help: `caddy --help`
Help about a command: `caddy respond --help`

### Server
Start server: `caddy start`
Stop server: `caddy stop`

### Test server
1. Start: `caddy respond -l :8082 -s 201 -b "Hello, Caddy" -H "Header1: Value1"`
2. Test: `curl -i localhost:8082`
