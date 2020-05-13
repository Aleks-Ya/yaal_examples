# Encryption

1. Create a broker certificate
Generate key pair:
```
keytool -genkey -noprompt \
	-alias localhost \
	-dname "CN=kia.manning.com, OU=TEST, O=MASQUERADE, L=Portland, C=US" \
	-keystore kafka.broker0.keystore.jks \
	-keyalg RSA \
	-storepass masquerade \
	-keypass masquerade \
	-validity 365
```
??? How to use PKCS12 instead of JKS? (`-deststoretype pkcs12` with the same storepass and keypass)

2. Create CA cerificate
```
openssl req -new -x509 -keyout ca.key -out ca.crt \
-days 999 -subj '/CN=localhost/OU=TEST/O=MASQUERADE/L=Portland/C=US' \
-passin pass:masquerade -passout pass:masquerade
```

3. Import CA cert to truststores
```
keytool -keystore kafka.client.truststore.jks \
	-alias CARoot -import -file ca.crt -keypass masquerade -storepass masquerade -noprompt
keytool -keystore kafka.server.truststore.jks \
	-alias CARoot -import -file ca.crt -keypass masquerade -storepass masquerade -noprompt
```
4. Extract the key from the broker keystore (create a certificate request)
```
keytool -keystore kafka.broker0.keystore.jks \
	-alias localhost -certreq -file cert-file -storepass masquerade -noprompt
```

5. Sign broker certificate with CA cert:
```
openssl x509 -req -CA ca.crt -CAkey ca.key \
	-in cert-file -out cert-signed -days 365 \
	-CAcreateserial -passin pass:masquerade
```

6. import the CA certificate into our broker0 keystore
```
keytool -keystore kafka.broker0.keystore.jks \
	-alias CARoot -import -file ca.crt -storepass masquerade -noprompt
```

7. place the signed certificate into the broker broker0 keystore as well
```
keytool -keystore kafka.broker0.keystore.jks \
	-alias localhost -import -file cert-signed -storepass masquerade -noprompt
```

8. Configure brokers
Add in `server.properties`:
```
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.truststore.location=/var/ssl/private/kafka.server.truststore.jks
ssl.truststore.password=masquerade
ssl.keystore.location=/var/ssl/private/kafka.broker0.keystore.jks
ssl.keystore.password=masquerade
ssl.key.password=masquerade
```

9. Configure client
```
security.protocol=SSL
ssl.truststore.location=/var/private/ssl/client.truststore.jks
ssl.truststore.password=masquerade
```

10. Test connection to SSL
```
kafka-console-producer.sh --broker-list localhost:9093 --topic testSSL \
	--producer.config client-ssl.properties
kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic testSSL \
	--consumer.config client-ssl.properties
```
