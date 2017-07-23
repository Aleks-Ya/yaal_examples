# Hive Docker Image

#### Source
https://hub.docker.com/r/mcapitanio/hive

#### Versions

Available versions: 1.2.1, 1.2.2, 2.1.0, 2.1.1

All versions: https://hub.docker.com/r/mcapitanio/hive/tags/

#### Run

```
export HIVE_VERSION=1.2.1

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}
```

3. Test
URL: http://localhost:9200
Pass: elastic/changeme
