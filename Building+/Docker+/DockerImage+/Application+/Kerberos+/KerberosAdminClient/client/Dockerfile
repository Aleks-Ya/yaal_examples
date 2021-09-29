# Run Kerberos client

FROM debian-updated:stable

RUN apt-get install -y krb5-user

ENV KRB5_CONFIG=/tmp/kerberos/krb5.conf

CMD kadmin -s kdc.kerberos.yaal.ru:749 -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU -q "xst -norandkey -k /tmp/john.keytab john@ATHENA.MIT.EDU" \
    && /bin/bash
