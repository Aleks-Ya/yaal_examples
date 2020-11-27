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
    -days 999 -subj '/CN=CA/OU=Kitchen/O=Home/L=Anapa/C=RU' \
    -passin pass:capass -passout pass:capass
```

2. Generate broker private key and a keystore (password `br0pass`)
```
keytool -genkey -noprompt \
    -alias broker0 \
    -dname "CN=Broker0, OU=Kitchen, O=Home, L=Anapa, C=RU" \
    -keystore broker0.keystore \
    -keyalg RSA \
    -storepass br0pass \
    -keypass br0pass \
    -validity 365 \
    -deststoretype pkcs12 
```

3. Create client (password `clpass`) and server truststories and import CA cert into its
```
keytool -keystore client.truststore -deststoretype pkcs12 -alias caroot -import -file ca.crt -keypass clpass -storepass clpass -noprompt
keytool -keystore broker0.truststore -deststoretype pkcs12 -alias caroot -import -file ca.crt -keypass br0pass -storepass br0pass -noprompt
```

4. Create a certificate sign request (CSR) for broker public key
`keytool -keystore broker0.keystore -alias broker0 -certreq -file broker0.csr -storepass br0pass -noprompt`

5. Sign broker certificate with CA cert:
Create extensions file: `echo "subjectAltName=DNS:localhost,IP:127.0.0.1" > san.ext`
Sign the CSR:
```
openssl x509 -req -CA ca.crt -CAkey ca.key \
	-in broker0.csr -out broker0.crt -days 365 \
	-CAcreateserial -passin pass:capass -extfile san.ext
```

6. Import the CA certificate into our broker0 keystore
`keytool -keystore broker0.keystore -alias caroot -import -file ca.crt -storepass br0pass -noprompt`

7. Place the signed certificate into the broker broker0 keystore as well
`keytool -keystore broker0.keystore -alias broker0 -import -file broker0.crt -storepass br0pass -noprompt`

8. Configure brokers
Add in `server.properties`:
```
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.truststore.location=/home/aleks/tmp/encr/broker0.truststore
ssl.truststore.password=br0pass
ssl.keystore.location=/home/aleks/tmp/encr/broker0.keystore
ssl.keystore.password=br0pass
ssl.key.password=br0pass
```

9. Create client config file `client.properties`
```
security.protocol=SSL
ssl.truststore.location=/home/aleks/tmp/encr/client.truststore
ssl.truststore.password=clpass
```

10. Test connection to SSL
Create topic:
```
bin/kafka-topics.sh --bootstrap-server localhost:9093 --create \
    --command-config /home/aleks/tmp/encr/client.properties \
    --replication-factor 1 --partitions 1 --topic testSSL 
```
Producer to topic:
`bin/kafka-console-producer.sh --broker-list localhost:9093 --producer.config /home/aleks/tmp/encr/client.properties --topic testSSL`
Consume from topic:
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 \
    --consumer.config /home/aleks/tmp/encr/client.properties \
    --from-beginning --topic testSSL
```
