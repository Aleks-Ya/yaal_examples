# Run Valkey in Docker

Site: https://valkey.io
GitHub: https://github.com/valkey-io/valkey
DockerHub: https://hub.docker.com/r/valkey/valkey

1. Run: `docker run --rm -it valkey/valkey`
2. Test:
	1. Connect: `valkey-cli -h localhost -p 6379`
	2. Ping: `localhost:6379> PING`

## Commands
Ping server: `PING`

### String
Set key: `SET mykey "Hello, Valkey!"`
Get key: `GET mykey`

### List
Add elements to a list: `LPUSH mylist "hello1" "hello2"`
Get all elements of a list: `LRANGE mylist 0 -1`
