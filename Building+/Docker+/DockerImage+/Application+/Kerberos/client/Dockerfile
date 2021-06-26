# Run Kerberos client

FROM debian:stable

RUN apt-get update && apt-get install -y nano net-tools telnet less htop
RUN apt-get install -y krb5-user

ADD krb5.conf /etc/krb5.conf

CMD kadmin -s aleks-pc:749 -p kadmin/admin@ATHENA.MIT.EDU -w the_kadmin_pass -r ATHENA.MIT.EDU -q "xst -norandkey -k /tmp/john.keytab john@ATHENA.MIT.EDU" \
    &&/bin/bash
