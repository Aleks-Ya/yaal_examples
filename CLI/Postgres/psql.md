# psql CLI

## Install
`sudo apt install -y postgresql-client`

## Info
Help: `psql --help`
Version: `psql -V`

## Connection
Connect: `PGPASSWORD=pgpass psql -h postgres-host -p 5432 -U pguser`
Execute a command: `PGPASSWORD=pgpass  psql -h postgres-host -p 5432 -U psuser -c 'CREATE DATABASE my_test_db'`

## Commands
List all commands: `psql --help=commands`
Help
- Show help: `\?`
- Show help about SQL command: `\h create table`
Database
- List databases: `\l`
- Show current database: `\connect`
- Use database: `\connect DBNAME`
Schema
- List schemas: `\dn`
Table
- List tables in "cbrf" schema (current database): `\dt cbrf.*`
- Show structure of table: `\d+ table_name`
Index
- List indexes (in default schema?): `\di`
- List indexes in schema: `\di my_schema.*`
View
- List views: `\dv`
- Show structure of view: `\d+ view_name`
Sequence
- List sequences in all schemas: `\ds *.*`
- List sequences in specific schema: `\ds pfa.*`
- Describe sequence:`\d pfa.bank_info_uid_seq`
Role, User, Group
- List all roles (=users, = groups): `\du` or `\dg`
Other
- List large objects: `\dl`
