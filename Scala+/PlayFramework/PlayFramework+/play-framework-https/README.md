# PlayFrameworkHttps

## Generate certificates
```
# Output dir
export OUT=/tmp/play/certs
mkdir -p $OUT
cd $OUT

# Generate private keys
openssl genrsa -out ca-root-private.pem
openssl genrsa -out ca-intermediate-private.pem
openssl genrsa -out server-private.pem

# Create CaRoot self-signed certificate
echo "authorityKeyIdentifier=keyid,issuer
	basicConstraints=CA:TRUE
	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyCertSign" > ca-root-v3.ext
openssl req -new -key ca-root-private.pem -out ca-root.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Ca Root'
openssl x509 -req -days 365 -in ca-root.csr -signkey ca-root-private.pem -outform PEM -out ca-root.pem -extfile ca-root-v3.ext

# Create IntermediateRoot certificate
echo "authorityKeyIdentifier=keyid,issuer
	basicConstraints=CA:TRUE
	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyCertSign" > ca-intermediate-v3.ext
openssl req -new -key ca-intermediate-private.pem -out ca-intermediate.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Ca Intermediate'
openssl x509 -req -days 365 -in ca-intermediate.csr -CA ca-root.pem -CAkey ca-root-private.pem -out ca-intermediate.pem -outform PEM -CAcreateserial -extfile ca-intermediate-v3.ext

# Create Server certificate
echo "authorityKeyIdentifier=keyid,issuer
	basicConstraints=CA:FALSE
	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
	subjectAltName=DNS:localhost,IP:127.0.0.1" > server-v3.ext
openssl req -new -key server-private.pem -out server.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Server'
openssl x509 -req -days 365 -in server.csr -CA ca-intermediate.pem -CAkey ca-intermediate-private.pem -out server.pem -outform PEM -CAcreateserial -extfile server-v3.ext

# Create server_keystore
openssl pkcs12 -export -in server.pem -inkey server-private.pem -name localhost -out server_keystore.p12 -CAfile ca-root.crt -caname root -passout pass:456789 
keytool -importkeystore -deststorepass 098765 -destkeypass 098765 -destkeystore server_keystore.jks -srckeystore server_keystore.p12 -srcstoretype PKCS12 -srcstorepass 456789 -alias localhost
keytool -import -noprompt -alias ca-intermediate -file ca-intermediate.pem -keystore server_keystore.jks -storepass 098765
#(Optional) keytool -list -keystore server_keystore.jks -storepass 098765

# Create client_truststore
keytool -import -noprompt -alias ca-root -file ca-root.pem -keystore client_truststore.jks -storepass 654321

```

## Test with a browser
1. Run the app 
```
sbt -Dhttps.port=9443 \
    -Dplay.server.https.keyStore.path=PlayFramework+/play-framework-https/conf/server_keystore.jks \
    -Dplay.server.https.keyStore.password=098765 \
    "project playFrameworkHttps" run
```
2. Open http://localhost:9000
3. Open https://localhost:9443 (not valid certificate)
