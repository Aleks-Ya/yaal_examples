# Commands

## Run OpenSSL CLI
`docker run -it --rm --name OpenSslCertChain openssl:1` (use image from OpenSSL example).

## Generate a key pair
### Root CA key pair
Root CA key pair: `openssl genrsa -out ca_root.key.pem 2048`
Print key pair: `openssl rsa -in ca_root.key.pem -text -noout`

### Intermediate CA key pair
Intermediate CA key pair: `openssl genrsa -out ca_intermediate.key.pem 2048`
Print key pair: `openssl rsa -in ca_intermediate.key.pem -text -noout`

### Client key pair
Client CA key pair: `openssl genrsa -out client.key.pem 2048`
Print key pair: `openssl rsa -in client.key.pem -text -noout`

Properties:
1. Algorithm: `RSA`
2. File format: `PEM`
3. Key size: `2048`

## Create Cerficate Sign Requests
Properties:
1. No password protection

### Root CA CSR
```
openssl req -new -sha256 -key ca_root.key.pem -out ca_root.csr.pem \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=RootCA/CN=root.ca.ru" -nodes
```

Verify and print CSR: `openssl req -verify -in ca_root.csr.pem -text -noout`

### Intermediate CA CSR
```
openssl req -new -sha256 -key ca_intermediate.key.pem -out ca_intermediate.csr.pem \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=IntermediateCA/CN=intermediate.ca.ru" -nodes
```
Verify and print CSR: `openssl req -verify -in ca_intermediate.csr.pem -text -noout`

### Client CSR
```
openssl req -new -sha256 -key client.key.pem -out client.csr.pem \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=Client/CN=client.ru" -nodes
```
Verify and print CSRPrint CSR: `openssl req -verify -in client.csr.pem -text -noout`

## Sign CSRs
### Root CA certificate (self-signed)
Sign: 
```
openssl x509 -req -sha256 -days 365 -in ca_root.csr.pem \
    -signkey ca_root.key.pem -out ca_root.crt.pem
```
Print certificate: `openssl x509 -in ca_root.crt.pem -text -noout`
Verify self-signed certificate: `openssl verify -CAfile ca_root.crt.pem -CApath . ca_root.crt.pem`

### Intermediate CA certificate (signed by Root CA)
Sign: 
```
openssl x509 -req -sha256 -days 365 -CA ca_root.crt.pem -CAkey ca_root.key.pem -CAcreateserial \
    -in ca_intermediate.csr.pem -out ca_intermediate.crt.pem
```
Print certificate: `openssl x509 -in ca_intermediate.crt.pem -text -noout`
Verify certificate: `openssl verify -CAfile ca_root.crt.pem -CApath . ca_intermediate.crt.pem`

### Client certificate (signed by Intermediate CA)
Sign: 
```
openssl x509 -req -sha256 -days 365 -CA ca_intermediate.crt.pem -CAkey ca_intermediate.key.pem -CAcreateserial \
    -in client.csr.pem -out client.crt.pem
```
Print certificate: `openssl x509 -in client.crt.pem -text -noout`
Verify certificate: `openssl verify -CAfile ca_intermediate.crt.pem -CApath . client.crt.pem` (TODO fix this command)