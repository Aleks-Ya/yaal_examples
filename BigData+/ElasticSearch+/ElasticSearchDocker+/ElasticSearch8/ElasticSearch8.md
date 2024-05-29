# ElasticSearch v8

## Info
Docs: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
GitHub: https://github.com/elastic/elasticsearch/tree/main/distribution/docker
Home: https://www.docker.elastic.co/
DockerHub: https://hub.docker.com/_/elasticsearch

## Run
### Cluster
1. Create a network: `docker network create elastic`
2. Run: `docker run --name es8 --rm --net elastic -p 9200:9200 -it -m 1GB docker.elastic.co/elasticsearch/elasticsearch:8.13.4`

### Single node
1. Run: `docker run --name es8 --rm -p 9200:9200 -it -m 1GB -e ELASTIC_PASSWORD=pass1 docker.elastic.co/elasticsearch/elasticsearch:8.13.4`
2. Test: `curl --insecure https://localhost:9200 -u elastic:pass1`

## Errors
Error `max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`.
Fix: `sudo sysctl -w vm.max_map_count=262144`
