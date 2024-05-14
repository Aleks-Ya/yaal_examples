# Links between services
Access services (delcared in `docker-compose.yml`) by their hostnames.

## Start
`docker-compose up`

## Check connection between Client and Server
- by `hostname`: `docker exec -it client-container wget http://server-hostname:80 -q -O -`
- by `container_name`: `docker exec -it client-container wget http://server-container:80 -q -O -`
- by service name: `docker exec -it client-container wget http://server-service:80 -q -O -`

## Stop
`docker-compose down`
