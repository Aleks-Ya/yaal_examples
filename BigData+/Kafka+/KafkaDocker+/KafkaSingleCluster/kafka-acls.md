# kafka-acls CLI

## List ACLs
```
kafka-acls.sh \
    --authorizer-properties zookeeper.connect=$ZK \
    --list
```

## Create ACL
```
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

## Remove ACL
```
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
