# MySQL

[Official Docker container](https://hub.docker.com/_/mysql/)

## Run
`docker run --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass1 mysql`

## Connect to MySQL CLI
```
# Inside of container
docker exec -it mysql mysql -h 127.0.0.1 -P 3306 --user=root --password=pass1

# Outside of container
sudo apt install -y mysql-client
mysql -h 127.0.0.1 -P 3306 --user=root --password=pass1
```

## Errors
### Public Key Retrieval is not allowed
DBeaver shows error message: `Public Key Retrieval is not allowed`
Cause: ?
Fix: connect to MySQL from Terminal (`mysql -h 127.0.0.1 -P 3306 --user=root --password=pass1`)
