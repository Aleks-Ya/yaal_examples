# ElasticSearch Docker Image

## Sources

[Link](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

## Run the original build

```
export ELASTIC_VERSION=5.5.0

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  --name elastic \
  docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}
```

## Run customzed build
```
docker build --tag=elasticsearch-custom .

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  --name elastic \
  elasticsearch-custom
```

## Test

URL: http://localhost:9200

Pass: elastic/changeme

## Connect to Docker container with Bash
```
docker exec -it elastic bash
```
