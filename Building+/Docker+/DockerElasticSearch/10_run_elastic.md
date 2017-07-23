# ElasticSearch Docker Image

## Source:

https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html

## Run

```
export ELASTIC_VERSION=5.5.0

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}
```

## Test

URL: http://localhost:9200

Pass: elastic/changeme
