# Encryption

## Target files
1. CA
    1. ca.key
    2. ca.crt
2. Broker
    1. broker.csr
    2. broker.crt
    3. broker.keystore
        1. Broker private key (alias `localhost`)
        2. ca.crt (alias `caroot`)
    4. broker.truststore
        1. ca.crt (alias `caroot`)
3. Bob
    1. bob.csr
    2. bob.crt
    3. bob.keystore
        1. Bob private key (alias `localhost`)
        2. ca.crt (alias `caroot`)
    4. bob.truststore
        1. ca.crt (alias `caroot`) 
4. Kate
    1. kate.csr
    2. kate.crt
    3. kate.keystore
        1. Kate private key (alias `localhost`)
        2. ca.crt (alias `caroot`)
    4. client.truststore
        1. ca.crt (alias `caroot`) 

1. Generate CA private key and self-signed cerificate (password `capass`)
```
openssl req -new -x509 -keyout ca.key -out ca.crt \
    -days 999 -subj '/CN=CA/OU=Kitchen/O=Home/L=Anapa/C=RU' \
    -passin pass:capass -passout pass:capass
```

2. Generate broker private key and a keystore (password `brpass`)
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

3. Generate Bob private key and a keystore (password `bobpass`)
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

4. Generate Kate private key and a keystore (password `katepass`)
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

3. Create bob, kate and borker truststories and import CA cert into its
```
keytool -keystore broker.truststore -deststoretype pkcs12 -alias caroot -import -file ca.crt -keypass brpass -storepass brpass -noprompt
keytool -keystore bob.truststore -deststoretype pkcs12 -alias caroot -import -file ca.crt -keypass bobpass -storepass bobpass -noprompt
keytool -keystore kate.truststore -deststoretype pkcs12 -alias caroot -import -file ca.crt -keypass katepass -storepass katepass -noprompt
```

4. Create a certificate sign requests (CSR) for public keys
```
keytool -keystore broker.keystore -alias broker -certreq -file broker.csr -storepass brpass -noprompt
keytool -keystore bob.keystore -alias bob -certreq -file bob.csr -storepass bobpass -noprompt
keytool -keystore kate.keystore -alias kate -certreq -file kate.csr -storepass katepass -noprompt
```

5. Sign CSRs with CA cert:
```
echo "subjectAltName=DNS:localhost,IP:127.0.0.1" > broker.ext
openssl x509 -req -CA ca.crt -CAkey ca.key -in broker.csr -out broker.crt -days 365 -CAcreateserial -passin pass:capass -extfile broker.ext
openssl x509 -req -CA ca.crt -CAkey ca.key -in bob.csr -out bob.crt -days 365 -CAcreateserial -passin pass:capass
openssl x509 -req -CA ca.crt -CAkey ca.key -in kate.csr -out kate.crt -days 365 -CAcreateserial -passin pass:capass
```

6. Import the CA certificates into keystores
```
keytool -keystore broker.keystore -alias caroot -import -file ca.crt -storepass brpass -noprompt
keytool -keystore bob.keystore -alias caroot -import -file ca.crt -storepass bobpass -noprompt
keytool -keystore kate.keystore -alias caroot -import -file ca.crt -storepass katepass -noprompt
```

7. Place the signed certificates into keystores
```
keytool -keystore broker.keystore -alias broker -import -file broker.crt -storepass brpass -noprompt
keytool -keystore bob.keystore -alias bob -import -file bob.crt -storepass bobpass -noprompt
keytool -keystore kate.keystore -alias kate -import -file kate.crt -storepass katepass -noprompt
```

8. Configure brokers
Add in `server.properties`:
```
ssl.client.auth=required
authorizer.class.name=kafka.security.authorizer.AclAuthorizer
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.truststore.location=/home/aleks/tmp/auth/broker.truststore
ssl.truststore.password=brpass
ssl.keystore.location=/home/aleks/tmp/auth/broker.keystore
ssl.keystore.password=brpass
ssl.key.password=brpass
```

9. Create Bob config file `bob.properties`
```
group.id=bob-consumer-group
security.protocol=SSL
ssl.truststore.location=/home/aleks/tmp/auth/bob.truststore
ssl.truststore.password=bobpass
ssl.keystore.location=/home/aleks/tmp/auth/bob.keystore
ssl.keystore.password=bobpass
ssl.key.password=bobpass
```

10. Create Bob config file `kate.properties`
```
group.id=kate-consumer-group
security.protocol=SSL
ssl.truststore.location=/home/aleks/tmp/auth/kate.truststore
ssl.truststore.password=katepass
ssl.keystore.location=/home/aleks/tmp/auth/kate.keystore
ssl.keystore.password=katepass
ssl.key.password=katepass
```

11. Configure server
1. Run Zookeeper and Kafka Server
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

2. Fix `ClusterAuthorizationException`:
```
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 \
    --add \
    --allow-principal User:ANONYMOUS \
    --operation All \
    --cluster
```

12. Create ACLs
For Bob:
Create topic privileges:
```
bin/kafka-acls.sh --bootstrap-server localhost:9092 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --operation Create \
    --topic testSSL
```

Producer privileges:
```
#Via Bootstrap server:
bin/kafka-acls.sh --bootstrap-server localhost:9092 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --operation Write \
    --topic testSSL

#Via Zookeeper:
bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --operation Write \
    --topic testSSL
```
Give consumer privileges:
```
# Read operation
bin/kafka-acls.sh --bootstrap-server localhost:9092 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --operation Read \
    --topic testSSL

bin/kafka-acls.sh --bootstrap-server localhost:9092 \
    --add \
    --allow-principal User:CN=Bob,OU=Kitchen,O=Home,L=Anapa,C=RU \
    --group bob-consumer-group
```

List ACLs:
Via Zookeeper: `bin/kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 --list`
Via Bootstrap servers: `bin/kafka-acls.sh --bootstrap-server localhost:9092 --list`

12. Test connection to SSL
Create topic:
```
bin/kafka-topics.sh --bootstrap-server localhost:9093 --create \
    --command-config /home/aleks/tmp/auth/bob.properties \
    --replication-factor 1 --partitions 1 --topic testSSL
```
Producer to topic:
`bin/kafka-console-producer.sh --broker-list localhost:9093 --producer.config /home/aleks/tmp/auth/bob.properties --topic testSSL`
Consume from topic:
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --from-beginning \
    --consumer.config /home/aleks/tmp/auth/bob.properties --topic testSSL
```
