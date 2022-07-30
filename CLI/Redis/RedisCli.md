# Redis CLI

## Install
`sudo snap install redis`

## Info
Help: `redis-cli --help`

## Run CLI
Connect to default: `redis-cli`
Connect to host: `redis-cli -h localhost -p 6379`

## Send a command as parameter
`redis-cli ping`

## Send command in CLI
1. Run CLI `redis-cli`
2. Enter command: `ping`


## Commands
### Other
Exit: `EXIT`
Check connection: `PING`
Save a string: `SET person "John Dow"`
Read a string: `GET person`
Create a counter: `SET visits 1`
Increment a counter: `INCR visits`
Create a bitmap: `SETBIT mykey 7 1`
Get a bit from a bitmap: `GETBIT mykey 7`
Create a list: `LPUSH persons John Mary Ann`
Get an element of a list: `LINDEX persons 0`
Show all elements in a list: `LRANGE persons 0 -1`
Create a set: `SADD fruits Orange Pear`
Show all elements of a set: `SMEMBERS fruits`
Does a set contain an element: `SISMEMBER fruits Orange`
Create a Hash: `HSET visitor1 name "John" age "30"`
Get an field of a Hash: `HGET visitor1 age`
List all keys: `KEYS '*'`
List all databases: `INFO KEYSPACE`
Show key number in databases: `INFO KEYSPACE`
Delete a key: `DEL key1`

### Security
Require pass: `CONFIG SET requirepass "pass1"`
Authenticate CLI: `AUTH "pass1"`