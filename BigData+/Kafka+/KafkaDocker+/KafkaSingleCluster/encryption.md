# Encryption

Target files:
1. ca.key
2. ca.crt
3. broker0.csr
4. broker0.crt
5. broker0.keystore
	1. Broker private key (alias `localhost`)
	2. ca.crt (alias `caroot`)
4. client.truststore
	1. ca.crt (alias `caroot`) 
5. broker0.truststore
	1. ca.crt (alias `caroot`)  

1. Generate CA private key and self-signed cerificate (password `capass`)
```
openssl req -new -x509 -keyout ca.key -out ca.crt \
    -days 999 -subj '/CN=localhost/OU=TEST/O=MASQUERADE/L=Portland/C=US' \
    -passin pass:capass -passout pass:capass
```

2. Generate broker private key and a keystore (password `br0pass`)
```
keytool -genkey -noprompt \
    -alias localhost \
    -dname "CN=localhost, OU=TEST, O=MASQUERADE, L=Portland, C=US" \
    -keystore broker0.keystore \
    -keyalg RSA \
    -storepass br0pass \
    -keypass br0pass \
    -validity 365 \
    -deststoretype pkcs12
```

3. Create client (password `clpass`) and server truststories and import CA cert into its
```
keytool -keystore client.truststore -deststoretype pkcs12 -alias CARoot -import -file ca.crt -keypass clpass -storepass clpass -noprompt
keytool -keystore broker0.truststore -deststoretype pkcs12 -alias CARoot -import -file ca.crt -keypass br0pass -storepass br0pass -noprompt
```

4. Create a certificate sign request (CSR) for broker public key
`keytool -keystore broker0.keystore -alias localhost -certreq -file broker0.csr -storepass br0pass -noprompt`

5. Sign broker certificate with CA cert:
```
openssl x509 -req -CA ca.crt -CAkey ca.key \
	-in broker0.csr -out broker0.crt -days 365 \
	-CAcreateserial -passin pass:capass
```

6. import the CA certificate into our broker0 keystore
`keytool -keystore broker0.keystore -alias CARoot -import -file ca.crt -storepass br0pass -noprompt`

7. place the signed certificate into the broker broker0 keystore as well
`keytool -keystore broker0.keystore -alias localhost -import -file broker0.crt -storepass br0pass -noprompt`

8. Configure brokers
Add in `server.properties`:
```
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.truststore.location=/var/ssl/private/broker0.truststore
ssl.truststore.password=masquerade
ssl.keystore.location=/var/ssl/private/broker0.keystore
ssl.keystore.password=masquerade
ssl.key.password=masquerade
```

9. Create client config file `client.properties`
```
security.protocol=SSL
ssl.truststore.location=/home/aleks/tmp/encr/client.truststore
ssl.truststore.password=clpass
```

10. Test connection to SSL
Create topic:
`kafka-topics.sh --bootstrap-server localhost:9093 --create --replication-factor 1 --partitions 1 --topic testSSL --command-config /home/aleks/tmp/encr/client.properties`
Producer to topic:
`kafka-console-producer.sh --broker-list localhost:9093 --topic testSSL --producer.config /home/aleks/tmp/encr/client.properties`
Consume from topic:
`kafka-console-consumer.sh --bootstrap-server localhost:9093 --from-beginning --topic testSSL --consumer.config /home/aleks/tmp/encr/client.properties`
