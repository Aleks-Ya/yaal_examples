# OpenSSL

## Run OpenSSL CLI
1. Build image: `docker build --tag openssl:1 .`
1. Run container: `docker run -it --rm --name OpenSSL -v $(pwd):/root openssl:1`

## Commands
- Show help: `openssl help`
- Generate RSA private key: `openssl genrsa -out my.key.pem 2048`
- Extract RSA public key from private key: `openssl rsa -in my.key.pem -outform PEM -pubout -out public.pem`
- Generate Certificate Signing Request: `openssl req -new -sha256 -key key.pem -out my.domain.com.csr.pem`
- Convert certificate chain to openable cert: `openssl pkcs7 -outform PEM -in cert.p7b -print_certs > certificate_bundle.cer.pem`
- Print certificate: `openssl x509 -in my.crt -text -noout`
- Print certificate Serial Number: `openssl x509 -serial -noout -in ca.crt.pem`

### Online Certificate Status Protocol (OCSP)
- Print OCSP URI: `openssl x509 -noout -ocsp_uri -in wikipedia.crt.pem`

### Fingerprint
- Show SHA256 fingerprint: `openssl x509 -in wikipedia.crt.pem -noout -sha256 -fingerprint`