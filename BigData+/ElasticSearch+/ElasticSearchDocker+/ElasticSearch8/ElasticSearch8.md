# ElasticSearch v8

## Info
Docs: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
GitHub: https://github.com/elastic/elasticsearch/tree/main/distribution/docker
Home: https://www.docker.elastic.co/

## Run
1. Create a network: `docker network create elastic`
2. Run: `docker run --name elastic-search-8 --rm --net elastic -p 9200:9200 -it -m 1GB docker.elastic.co/elasticsearch/elasticsearch:8.10.4`
2. Run: `docker run --name elastic-search-8 --rm -p 9200:9200 -it -m 1GB docker.elastic.co/elasticsearch/elasticsearch:8.10.4`