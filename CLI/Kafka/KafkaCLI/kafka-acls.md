# kafka-acls.sh CLI

Docs: [Command Line Interface](https://kafka.apache.org/documentation/#security_authz_cli)

## List ACLs
`kafka-acls --bootstrap-server localhost:9092 --list`


## Deprecated (via ZooKeeper)
### List ACLs
```shell
kafka-acls.sh \
    --authorizer-properties zookeeper.connect=$ZK \
    --list
```
### Create ACL
```shell
kafka-acls.sh \
    --authorizer-properties zookeeper.connect=$ZK \
    --add \
    --allow-principal User:Bob \
    --allow-principal User:Alice \
    --allow-host 198.51.100.0 \
    --operation Read \
    --operation Write \
    --topic Test-topic
```
### Remove ACL
```shell
kafka-acls.sh \
    --authorizer-properties zookeeper.connect=$ZK \
    --remove \
    --allow-principal User:Bob \
    --allow-principal User:Alice \
    --allow-host 198.51.100.0 \
    --operation Read \
    --operation Write \
    --topic Test-topic
```


