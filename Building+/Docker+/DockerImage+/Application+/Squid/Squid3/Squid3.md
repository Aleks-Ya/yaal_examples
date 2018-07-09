# Run Squid 3.x
## Info
Sources: https://hub.docker.com/r/scbunn/squid/

## Run
`docker run -d -p 3128:3128 --name squid scbunn/squid:latest`

## Check
`google-chrome --proxy-server=http://localhost:3128`

## Stop and start
```
docker stop squid
docker start squid
```

## See logs
```
docker exec squid tail -f /var/log/squid/access.log
docker exec squid tail -f /var/log/squid/cache.log
```

## See config
`docker exec squid cat /etc/squid/squid.conf`
