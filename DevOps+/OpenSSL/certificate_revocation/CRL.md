# Certificate revocation

## CRT examples
Download and print CRT:
```
wget http://onsitecrl.verisign.com/CertiNetSAFirmaElectronicaAvanzadaG2/LatestCRL.crl
openssl crl -inform DER -in LatestCRL.crl -noout -text
```

Download CRT file: 
- http://onsitecrl.verisign.com/CertiNetSAFirmaElectronicaAvanzadaG2/LatestCRL.crl
- http://crl.globalsign.com/gs/gsorganizationvalsha2g2.crl

## Commands
### Run OpenSSL CLI
`docker run -it --rm --name OpenSslCrl openssl:1` (use image from OpenSSL example).

### Show current config file
`cat /etc/ssl/openssl.cnf`

### Create CA key pair and self-signed certificate
1. Create CA key pair
Root CA key pair: `openssl genrsa -out ca.key.pem 2048`
Print key pair: `openssl rsa -in ca.key.pem -text -noout`

2. Generate CA CSR
```
openssl req -new -sha256 -key ca.key.pem -out ca.csr.pem \
    -subj "/C=RU/ST=Leningrad/L=SPb/O=RootCA/CN=ca.ru" -nodes
```
Verify and print CSR: `openssl req -verify -in ca.csr.pem -text -noout`

3. Sign CA certificate (self-signed)
Sign: `openssl x509 -req -sha256 -days 365 -in ca.csr.pem -signkey ca.key.pem -out ca.crt.pem`
Print certificate: `openssl x509 -in ca.crt.pem -text -noout`
Verify self-signed certificate: `openssl verify -CAfile ca.crt.pem -CApath . ca.crt.pem`

### Create new CRT 
1. Append config file:
```
(
    echo '[ server_cert ]'
    echo 'crlDistributionPoints = URI:http://example.com/intermediate.crl.pem'
 ) >> /etc/ssl/openssl.cnf
```
2. Create files:
```
mkdir ./demoCA
touch ./demoCA/index.txt
echo 00 > ./demoCA/crlnumber
```
3. Create CRT: `openssl ca -gencrl -cert ca.crt.pem -keyfile ca.key.pem -out ca.crl.pem`
4. Print CRT: `openssl crl -in ca.crl.pem -noout -text`