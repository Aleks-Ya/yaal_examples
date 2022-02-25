# keytool CLI

Certificate

Print CER or PEM certificate details: 
```
keytool -printcert -v -file my_cert.cer
```
Print CSR details:
```
keytool -printcertreq -v -file broker.csr
```

Key

Create private key and self-signed certificate:
```
keytool -genkey -noprompt \
    -alias broker0 \
    -dname "CN=Broker0, OU=Kitchen, O=Home, L=Anapa, C=RU" \
    -keystore broker0.keystore \
    -keyalg RSA \
    -storepass br0pass \
    -keypass br0pass \
    -validity 365 \
    -deststoretype pkcs12
```
Change private key password:
```
keytool -keypasswd -keystore my_keystore.jks -storepass my_keystore_pass -alias my_key_alias -keypass my_old_key_pass -new my_new_key_pass
```

Keystore

List certificates in keystore or truststore:
```
# Short
keytool -list -keystore /usr/cacerts -storepass changeit
# Full (verbose)
keytool -list -v -keystore /usr/cacerts -storepass changeit
```
Add certificate to a truststore:
```
keytool -import -noprompt -alias the_alias -file self.cer -keystore the_keystore -storepass my_pass
```
Change keystore password:
```
keytool -storepasswd -keystore my_keystore.jks -storepass my_old_password -new my_new_password
```
Delete alias from a keystore:
```
keytool -delete -alias my_alias -keystore my_truststore.jks -storepass my_pass
```
Import all entries from one keystore to another:
```
keytool -importkeystore \
    -srckeystore src_keystore.jks -srcstorepass 654321 \
    -destkeystore dest_keystore.jks -deststorepass 123456
```
