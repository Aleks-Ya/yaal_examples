# Run Valkey in Docker

Site: https://valkey.io
GitHub: https://github.com/valkey-io/valkey
DockerHub: https://hub.docker.com/r/valkey/valkey

1. Run: `docker run --name valkey -d valkey/valkey`
2. Test:
	1. Connect: `valkey-cli -h localhost -p 6379`
	2. Ping: `localhost:6379> PING`

## Commands
Ping server: `PING`
Set key: `SET mykey "Hello, Valkey!"`
Get key: `GET mykey`
