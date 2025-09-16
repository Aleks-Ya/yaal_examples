# OpenSSL CLI

## Common
Help: `openssl help`
Show the OpenSSL dir: `openssl version -a`

## Keys
Export a public key to file: `openssl rsa -in private.key -pubout -out public.key`
Export a private key from P12: `openssl pkcs12 -in keystore.p12 -nodes -nocerts -out private_key.pem`
Show details of an RSA private key: `openssl rsa -text -in private.pem`
Show details of an RSA public key: `openssl rsa -text -pubin -in public.pem`
Generate new RSA key pair (without password):
```
# Generate private key
openssl genrsa -out rsa-private.pem 4096
# Extract public key
openssl rsa -in rsa-private.pem -pubout > rsa-public.pem
```
Generate new RSA key pair (with password):
```
# Put password to a file
echo "the_password" > password.tmp
# Generate private key
openssl genrsa -out rsa-private.pem -passout file:password.tmp 4096
# Extract public key
openssl rsa -in rsa-private.pem -pubout > rsa-public.pem
```
Convert PEM private key to PKCS8 (to DER format, without password):
```
# To DER format. Without password.
openssl pkcs8 -topk8 -inform PEM -outform DER -in rsa-private.pem -out rsa-private.der -nocrypt
# To PEM format. Without password.
openssl pkcs8 -topk8 -inform PEM -outform PEM -in rsa-private.pem -out rsa-private.pem -nocrypt
# To PEM format. With password.
openssl pkcs8 -topk8 -inform PEM -outform PEM -in rsa-private.pem -out rsa-private.pem
```

## Certificate Sign Request (CSR)
Create a CSR:
```
# Manual subject entering
openssl req -new -key rsa-private.pem -out rsa.csr
# Subject in parameters
openssl req -new -key rsa-private.pem -out rsa.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Alex Iablokov'
```
Print CSR details: `openssl req -noout -text -in rsa.csr`

## Certificate
Show certificate info: `openssl x509 -text -in fd.crt -noout`
Create Certificate from CSR: `openssl x509 -req -days 365 -in rsa.csr -signkey rsa-private.pem -out rsa.crt`
Sing CSR with a CA certificate: `openssl x509 -req -days 365 -in server.csr -CA ca.crt -CAkey ca-private.pem -out server.crt -CAcreateserial`
Print Certificate details: `openssl x509 -in rsa.crt -noout -text`
Convert a DER file (.crt .cer .der) to PEM: `openssl x509 -inform der -in server.cer -out server.pem`
Printing certificate Serial Number: `openssl x509 -serial -noout -in my.crt`

## Verify
Verify certificate chain: `openssl verify -CAfile ca.crt bob.crt`

## Encrypt/Decrypt

### Using a text key
Encrypt and decrypt in single line:
Through Binary: `echo -n "My Text" | openssl enc -e -aes256 -pbkdf2 -k MyKey | openssl enc -d -aes256 -pbkdf2 -k MyKey`
Through Base64: `echo -n "My Text" | openssl enc -e -aes256 -pbkdf2 -k MyKey -base64 | openssl enc -d -aes256 -pbkdf2 -k MyKey -base64`

Encrypt and decrypt in seperately:
1. Encrypt: `echo -n "My Text" | openssl enc -e -aes256 -pbkdf2 -k MyKey -base64 > /tmp/encrypted.txt`
2. Decrypt: `cat /tmp/encrypted.txt | openssl enc -d -aes256 -pbkdf2 -k MyKey -base64`

### Using a key pair
Encrypt: `openssl pkeyutl -encrypt -pubin -inkey public.pem -in original.txt -out encrypted.bin`
Decrypt: `openssl pkeyutl -decrypt -inkey private.pem -in encrypted.bin -out decrypted.txt`
