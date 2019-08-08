# OCSP

## Commands
### Run OpenSSL CLI
`docker run -it --name OpenSslOcsp openssl:1` (use image from OpenSSL example).

### Check certificate against OCSP
Source: https://raymii.org/s/articles/OpenSSL_Manually_Verify_a_certificate_against_an_OCSP.html

1. Download certificate with OCSP:
`openssl s_client -connect wikipedia.org:443 2>&1 < /dev/null | sed -n '/-----BEGIN/,/-----END/p' > wikipedia.crt.pem`

2. Get OCSP URI:
`export OCSP_URI=$(openssl x509 -noout -ocsp_uri -in wikipedia.crt.pem)`

3. Create certification chain
List chain: `openssl s_client -connect wikipedia.org:443 -showcerts 2>&1 < /dev/null`
Copy all certificates except the cert under checking to file `chain.pem`

4. Send OCSP request
`openssl ocsp -issuer chain.pem -cert wikipedia.crt.pem -text -url $OCSP_URI`

