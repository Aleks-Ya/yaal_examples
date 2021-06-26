# Kerberos

## Prerequisites
Hostname should be `aleks-pc`.

## Build
KDC: `docker build -t kerberos-kdc kdc`
AdminClient: `docker build -t kerberos-admin-client admin_client`
Client: `docker build -t kerberos-client client`

## Run
KDC: `docker run --rm -it --network=host --name kerberos_kdc kerberos-kdc`
AdminClient: `docker run --rm -it --network=host --name kerberos_admiun_client kerberos-admin-client`
Client: `docker run --rm -it --network=host --name kerberos_client kerberos-client`

## Connect by `kadmin` to KDC 
Inside the KDC:
`kadmin -s aleks-pc -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU`
From the host OS:
```
export KRB5_CONFIG=/home/aleks/pr/home/yaal_examples/Building+/Docker+/DockerImage+/Application+/Kerberos/admin_client/krb5.conf
kadmin -s aleks-pc:749 -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU
```

## Extract keytab for John principal
On the KDC:
`kadmin.local xst -norandkey -k /tmp/john.keytab john@ATHENA.MIT.EDU`
On the host OS:
```
export KRB5_CONFIG=/home/aleks/pr/home/yaal_examples/Building+/Docker+/DockerImage+/Application+/Kerberos/admin_client/krb5.conf
kadmin -s aleks-pc:749 -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU -q "xst -norandkey -k /tmp/john.keytab john@ATHENA.MIT.EDU"
```

## Test Client to KDC connection
Using keytab: `kinit john@ATHENA.MIT.EDU -k -t /tmp/john.keytab`
Using password: `echo "the_john_pass" | kinit john@ATHENA.MIT.EDU`

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`
