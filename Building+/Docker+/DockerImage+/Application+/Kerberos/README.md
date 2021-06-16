# Fedora Docker image

## Build
Server: `docker build -t kerberos-server server`
Client: `docker build -t kerberos-client client`

## Run
Server: `docker run --rm -it --name kerberos_server kerberos-server`
Client: `docker run --rm -it --name kerberos_client kerberos-client`

## Create a realm
On server:
`krb5_newrealm`
The database Master Password: `the_db_master_pass`

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`
