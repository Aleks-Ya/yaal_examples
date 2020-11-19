# Kafka Standalone (run Kafka from ZIP)

## Download
1. https://kafka.apache.org/downloads
2. Unpack

## Run Zookeeper and Kafka Server
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

## Topic
Create topic: `bin/kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic test`
List topics: `bin/kafka-topics.sh --zookeeper localhost:2181 --list`
Delete topic: `bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic test`
Describe topic: `bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic test`
Produce a record: `bin/kafka-console-producer.sh --broker-list=localhost:9092 --topic=acl-test`
Consume all records: `bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic=acl-test --from-beginning`

## ACL 
List ACLs: `bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --list`
Create ACL: `bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:Bob --operation All --topic acl-topic`
Delete ACL: `bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --remove --allow-principal User:Bob --operation All --topic acl-topic`

## ACL example
Configure server: Append `authorizer.class.name=kafka.security.authorizer.AclAuthorizer` to `config/server.properties`
Restart server: `bin/kafka-server-stop.sh` and `bin/kafka-server-start.sh config/server.properties`
Set env: `export TOPIC=acl-example`
Create topic: `bin/kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic $TOPIC`
Produce a record: `bin/kafka-console-producer.sh --broker-list=localhost:9092 --topic=$TOPIC`
Consume all records: `bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic=acl-test --from-beginning`
Create ACL: `bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add --allow-principal User:* --operation All --topic $TOPIC`


### Generate key pairs and keystores
1. Generate CA key pair (password `capass`)
```
keytool -genkey -noprompt \
    -alias ca \
    -dname "CN=CA,OU=Kitchen,O=Home,L=Anapa,C=RU" \
    -keystore ca.keystore \
    -keyalg RSA \
    -storepass capass \
    -keypass capass \
    -validity 365 \
    -deststoretype pkcs12
```
2. Generate broker key pair (password `brpass`)
```
keytool -genkey -noprompt \
    -alias broker \
    -dname "CN=Broker,OU=Kitchen,O=Home,L=Anapa,C=RU" \
    -keystore broker.keystore \
    -keyalg RSA \
    -storepass brpass \
    -keypass brpass \
    -validity 365 \
    -deststoretype pkcs12
```
3. Generate Bob key pair (password `bobpass`)
```
keytool -genkey -noprompt \
    -alias bob \
    -dname "CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU" \
    -keystore bob.keystore \
    -keyalg RSA \
    -storepass bobpass \
    -keypass bobpass \
    -validity 365 \
    -deststoretype pkcs12
```
4. Generate Kate key pair (password `katepass`)
```
keytool -genkey -noprompt \
    -alias kate \
    -dname "CN=Kate,OU=Kitchen,O=Home,L=Anapa,C=RU" \
    -keystore kate.keystore \
    -keyalg RSA \
    -storepass katepass \
    -keypass katepass \
    -validity 365 \
    -deststoretype pkcs12
```
 
### Generate certificates
1. Generate CA certificate (self-signed)
```
openssl req -new -x509 -keyout ca.key -out ca.crt \
    -days 999 -subj '/CN=CA/OU=Kitchen/O=Home/L=Anapa/C=RU' \
    -passin pass:capass -passout pass:capass
```
2. Generate broker certificate
Create CSR and sign it with CA cert:
```
keytool -keystore broker.keystore -alias broker -certreq -file broker.csr -storepass brpass -noprompt
openssl x509 -req -CA ca.crt -CAkey ca.key \
    -in broker.csr -out broker.crt -days 365 \
    -CAcreateserial -passin pass:capass
```
3. Generate Bob certificate
Create CSR and sign it with CA cert:
```
keytool -keystore bob.keystore -alias bob -certreq -file bob.csr -storepass bobpass -noprompt
openssl x509 -req -CA ca.crt -CAkey ca.key \
    -in bob.csr -out bob.crt -days 365 \
    -CAcreateserial -passin pass:capass
```
4. Generate Kate certificate
Create CSR and sign it with CA cert:
```
keytool -keystore kate.keystore -alias kate -certreq -file kate.csr -storepass katepass -noprompt
openssl x509 -req -CA ca.crt -CAkey ca.key \
    -in kate.csr -out kate.crt -days 365 \
    -CAcreateserial -passin pass:capass
```

### Generate truststores
```
keytool -keystore broker.truststore -alias CA -import -file ca.crt -keypass brpass -storepass brpass -noprompt -deststoretype pkcs12
keytool -keystore bob.truststore -alias CA -import -file ca.crt -keypass bobpass -storepass bobpass -noprompt -deststoretype pkcs12
keytool -keystore kate.truststore -alias CA -import -file ca.crt -keypass katepass -storepass katepass -noprompt -deststoretype pkcs12
```

### Configure SSL
1. Server
Add in `conf/server.properties`:
```
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.truststore.location=/home/aleks/tmp/acl/broker.truststore
ssl.truststore.password=brpass
ssl.keystore.location=/home/aleks/tmp/acl/broker.keystore
ssl.keystore.password=brpass
ssl.key.password=brpass
```
2. Bob
Create file `bob.properties`:
```
ssl.truststore.location=/home/aleks/tmp/acl/bob.truststore
ssl.truststore.password=bobpass
ssl.keystore.location=/home/aleks/tmp/acl/bob.keystore
ssl.keystore.password=bobpass
ssl.key.password=bobpass
```
3. Kate
Create file `kate.properties`:
```
ssl.truststore.location=/home/aleks/tmp/acl/kate.truststore
ssl.truststore.password=katepass
ssl.keystore.location=/home/aleks/tmp/acl/kate.keystore
ssl.keystore.password=katepass
ssl.key.password=katepass
```

### Producer
Set env: `export TOPIC=acl-bob`
Create Bob's topic: `bin/kafka-topics.sh --zookeeper localhost:2181 --create --replication-factor 1 --partitions 1 --topic $TOPIC`
Create ACL: 
```
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --add \
    --allow-principal "User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU" --operation All --topic $TOPIC
```
Produce a record: `bin/kafka-console-producer.sh --broker-list=localhost:9093 --topic=$TOPIC --producer.config /home/aleks/tmp/acl/bob.properties`
Consume all records: `bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic=acl-test --from-beginning`


kafka-console-producer.sh --broker-list localhost:9093 --topic testSSL \
    