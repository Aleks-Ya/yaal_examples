# MySQL

[Official Docker container](https://hub.docker.com/_/mysql/)

## Run
```
docker run --name mysql5 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass -d mysql:5.7.21
```

## Connect to MySQL CLI
```
# Inside of container
docker exec -it mysql5 mysql -h 127.0.0.1 -P 3306 --user=root --password=pass

# Outside of container
sudo apt install -y mysql-client
mysql -h 127.0.0.1 -P 3306 --user=root --password=pass
```
