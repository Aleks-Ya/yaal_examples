# Run "fast-data-dev" Kafka cluster with activated ACL authrization

## Sources
Docker Hub: https://hub.docker.com/r/landoop/fast-data-dev

## Run
Build: `docker build -t fast-data-dev-acl .`
Run: `docker run --rm --net=host -e DEBUG=1 fast-data-dev-acl`

## Commands
Connect to the container by Bash:
`docker run --rm -it --net=host --entrypoint bash fast-data-dev-acl`

#### kafka-acls
Fix `ClusterAuthorizationException`:
```
kafka-acls --authorizer-properties zookeeper.connect=localhost:2181 \
    --add \
    --allow-principal User:ANONYMOUS \
    --operation All \
    --cluster
```

List ACLs:
`kafka-acls --bootstrap-server localhost:9092 --list`

Create ACL:
```
kafka-acls --bootstrap-server localhost:9092 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --operation Create \
    --topic testSSL
```