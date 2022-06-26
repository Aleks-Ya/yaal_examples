# Redis CLI

## Install
`sudo snap install redis`

## Info
Help: `redis-cli --help`

## Run CLI
`redis-cli`

## Send a command as parameter
`redis-cli ping`

## Send command in CLI
1. Run CLI `redis-cli`
2. Enter command: `ping`


## Commands
Exit: `EXIT`
Check connection: `PING`
Save a string: `SET person "John Dow"`
Read a string: `GET person`
Create a counter: `SET visits 1`
Incrementa a counter: `INCR visits`
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
