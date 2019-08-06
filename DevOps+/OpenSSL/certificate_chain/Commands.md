# Commands

## Run OpenSSL CLI
`docker run -it --name OpenSslCertChain openssl:1` (use image from OpenSSL example).

## Generate a key pair
### Root CA key pair
Root CA key pair: `openssl genrsa -out key_pair_ca_root.pem 2048`
Print key pair: `openssl rsa -in key_pair_ca_root.pem -text -noout`

### Intermediate CA key pair
Intermediate CA key pair: `openssl genrsa -out key_pair_ca_intermediate.pem 2048`
Print key pair: `openssl rsa -in key_pair_ca_intermediate.pem -text -noout`

### Client key pair
Client CA key pair: `openssl genrsa -out key_pair_client.pem 2048`
Print key pair: `openssl rsa -in key_pair_client.pem -text -noout`

Properties:
1. Algorithm: `RSA`
2. File format: `PEM`
3. Key size: `2048`

## Create Cerficate Sign Requests
Properties:
1. No password protection

### Root CA CSR
```
openssl req -new -sha256 -key key_pair_ca_root.pem -out ca_root.csr \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=RootCA/CN=root.ca.ru" -nodes
```

Verify and print CSR: `openssl req -verify -in ca_root.csr -text -noout`

### Intermediate CA CSR
```
openssl req -new -sha256 -key key_pair_ca_intermediate.pem -out ca_intermediate.csr \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=IntermediateCA/CN=intermediate.ca.ru" -nodes
```
Verify and print CSR: `openssl req -verify -in ca_intermediate.csr -text -noout`

### Client CSR
```
openssl req -new -sha256 -key key_pair_client.pem -out client.csr \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=Client/CN=client.ru" -nodes
```
Verify and print CSRPrint CSR: `openssl req -verify -in client.csr -text -noout`

## Sign CSRs
### Root CA certificate (self-signed)
Sign: 
```
openssl x509 -req -sha256 -days 365 -in ca_root.csr \
    -signkey key_pair_ca_root.pem -out ca_root.crt
```
Print certificate: `openssl x509 -in ca_root.crt -text -noout`
Verify self-signed certificate: `openssl verify -CAfile ca_root.crt -CApath . ca_root.crt`

### Intermediate CA certificate (signed by Root CA)
Sign: 
```
openssl x509 -req -sha256 -days 365 -CA ca_root.crt -CAkey key_pair_ca_root.pem -CAcreateserial \
    -in ca_intermediate.csr -out ca_intermediate.crt
```
Print certificate: `openssl x509 -in ca_intermediate.crt -text -noout`
Verify certificate: `openssl verify -CAfile ca_root.crt -CApath . ca_intermediate.crt`

### Client certificate (signed by Intermediate CA)
Sign: 
```
openssl x509 -req -sha256 -days 365 -CA ca_intermediate.crt -CAkey key_pair_ca_intermediate.pem -CAcreateserial \
    -in client.csr -out client.crt
```
Print certificate: `openssl x509 -in client.crt -text -noout`
Verify certificate: `openssl verify -CAfile ca_intermediate.crt -CApath . client.crt` (TODO fix this command)