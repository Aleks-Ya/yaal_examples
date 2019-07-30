# OpenSSL

## Run OpenSSL CLI
1. Build image: `docker build --tag openssl:1 .`
1. Run container: `docker run -it --rm --name OpenSSL -v $(pwd):/root openssl:1`

## Commands
- Show help: `openssl help`
- Generate RSA private key: `openssl genrsa -out key.pem 2048`
- Extract RSA public key from private key: `openssl rsa -in key.pem -outform PEM -pubout -out public.pem`
- Generate Certificate Signing Request: `openssl req -new -sha256 -key key.pem -out my.domain.com.csr`