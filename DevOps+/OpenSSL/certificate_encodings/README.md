# Certificate encodings

## Run OpenSSL CLI
`docker run -it --rm --name OpenSslCertEncodings openssl:1` (use image from OpenSSL example).

## Create certificates in PEM and DER encodings
1. Set env vars
```
export KEY_FILE=my.key.pem
export CSR_FILE=my.csr.pem
export CERT_PEM_FILE=my.crt.pem
export CERT_DER_FILE=my.crt.der
```

2. Create private key and CSR:
```
openssl req -new -sha256 -out $CSR_FILE -keyout $KEY_FILE \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=My/CN=my.ru" -nodes
```
Key is always encoded in PEM.
CSR can be created in DER encoding, but it's impossible to sign it.

3. Sign certificate in PEM encoding (by it self):
Sing: `openssl x509 -req -sha256 -days 365 -in $CSR_FILE -signkey $KEY_FILE -out $CERT_PEM_FILE`
View: `openssl x509 -in $CERT_PEM_FILE -text -noout`

4. Sign certificate in DER encoding (by it self):
Sing: `openssl x509 -req -sha256 -days 365 -in $CSR_FILE -signkey $KEY_FILE -out $CERT_DER_FILE -outform DER`
View: `openssl x509 -in $CERT_DER_FILE -text -noout -inform DER`
