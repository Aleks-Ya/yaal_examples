# ElasticSearch Docker Image

## Sources

[Link](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

## Run single node

### Run the original build

```
docker run --rm --name es7 \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  docker.elastic.co/elasticsearch/elasticsearch:7.17.17
```

### Run customized build
```
docker build --tag=elasticsearch-custom .

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  --name elastic \
  elasticsearch-custom
```

## Run cluster
Error `max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`.
Fix: `sudo sysctl -w vm.max_map_count=262144`
```
#Run
docker-compose -f docker-compose-6.2.1.yml up

#Stop containers, but keep volumes (can up again with the same data)
docker-compose -f docker-compose-6.2.1.yml down

#Stop containers, delete volumes (loose all data)
docker-compose -f docker-compose-6.2.1.yml down -v
```
## Test
`curl -u elastic:changeme http://localhost:9200`

## Connect to Docker container with Bash
`docker exec -it elastic bash`

## See logs
`docker logs --follow elastic`
