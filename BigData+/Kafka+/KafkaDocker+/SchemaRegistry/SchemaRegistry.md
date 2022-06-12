# Confluence Schema Registry

## Sources
Docker: https://hub.docker.com/r/confluent/schema-registry

## Run
```
docker run -d --name zookeeper -p 2181:2181 confluent/zookeeper
docker run -d --name schema-registry -p 8081:8081 --link zookeeper:zookeeper confluent/schema-registry
docker run -d --name rest-proxy -p 8082:8082 --link zookeeper:zookeeper --link schema-registry:schema-registry confluent/rest-proxy
```
