# MemCached Docker

DockerHub: https://hub.docker.com/_/memcached

1. Run: `docker run -it --rm -p 11211:11211 --name memcache memcached`
2. Test
	1. Store a key: `echo -ne "set mykey 0 900 4\r\nData\r\nquit\r\n" | nc localhost 11211`
	2. Get a key: `echo -ne "get mykey\r\nquit\r\n" | nc localhost 11211`
