#Run Kerberos Server in Docker

FROM debian:stable

RUN apt-get update && apt-get install -y krb5-kdc krb5-admin-server

ENV LOCALSTATEDIR=/usr/local/var
ENV DEFKTNAME=/etc/krb5.keytab
ADD krb5.conf ${DEFKTNAME}
ADD kdc.conf ${LOCALSTATEDIR}/krb5kdc/kdc.conf
ADD kadm5.acl ${LOCALSTATEDIR}/krb5kdc/kadm5.acl
RUN kdb5_util create -r ATHENA.MIT.EDU -s -P the_pass
#CMD while true; do :; done
CMD /bin/bash
