# Kerberos

## Build and run
`docker-compose down -v && docker-compose build && docker-compose up`

## Connect by `kadmin` to KDC 
Inside the KDC:
`kadmin -s aleks-pc -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU`
From the host OS:
```
export KRB5_CONFIG=/home/aleks/pr/home/yaal_examples/Building+/Docker+/DockerImage+/Application+/Kerberos/kdc/krb5.conf
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
Logout: `docker exec -it kerberos-client bash -c "kdestroy && klist"`
Using keytab: `docker exec -it kerberos-client bash -c "kinit john@ATHENA.MIT.EDU -k -t /tmp/john.keytab && klist"`
Using password: `docker exec -it kerberos-client bash -c "echo 'the_john_pass' | kinit john@ATHENA.MIT.EDU && klist"`

## Run bash
`docker run -it --net bridge --dns 8.8.8.8 --dns 10.66.0.6 fedora:25 /bin/bash`
