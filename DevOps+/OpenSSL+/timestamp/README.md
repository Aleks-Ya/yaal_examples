# Timestamp

## Run OpenSSL CLI
`docker run -it --rm --name OpenSslTimestamp openssl:1` (use image from OpenSSL example).

## Using freetsa.org
```
# Create a time stamp request
echo "my data" > data.txt
openssl ts -query -data data.txt -no_nonce -sha512 -out query.tsq

# Transtim reqeust to TSP server
curl -H "Content-Type: application/timestamp-query" --data-binary '@query.tsq' https://freetsa.org/tsr > response.tsr

# Donwload TSA certificates for verification
wget https://freetsa.org/files/tsa.crt
wget https://freetsa.org/files/cacert.pem

# Verify the time stamp (not recalulate data hash)
openssl ts -verify -in response.tsr -text -queryfile query.tsq -CAfile cacert.pem -untrusted tsa.crt

# Verify the time stamp (recalculate data hash)
openssl ts -verify -in response.tsr -data data.txt -CAfile cacert.pem -untrusted tsa.crt
```

## Using CryptoPro TSP test service

NOT WORK!!!!!!!!!
```
# Create a time stamp request
echo "my data" > data.txt
openssl ts -query -data data.txt -no_nonce -sha256 -out query.tsq

# Transtim reqeust to TSP server
curl -H "Content-Type: application/timestamp-query" --data-binary '@query.tsq' https://www.cryptopro.ru/tsp/tsp.srf > response.tsr

# Donwload TSA certificates for verification
wget http://www.cryptopro.ru/sites/default/files/products/tsp/tsa.cer

# Verify the time stamp (not recalulate data hash)
openssl ts -verify -in response.tsr -text -queryfile query.tsq -CAfile tsa.cer

# Verify the time stamp (recalculate data hash)
openssl ts -verify -in response.tsr -data data.txt -CAfile cacert.pem -untrusted tsa.crt
```
