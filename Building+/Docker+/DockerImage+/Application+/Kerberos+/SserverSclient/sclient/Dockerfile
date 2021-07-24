# Run sclient
FROM centos-updated:8

RUN yum install -y krb5-server krb5-workstation

ENV KERBEROS_SHARED=/tmp/kerberos
ENV KRB5_CONFIG="$KERBEROS_SHARED/krb5.conf"
ENV KEYTAB="$KERBEROS_SHARED/sclient.keytab"
ENV KRB5CCNAME=/tmp/krb5cc
ENV KRB5_TRACE=/dev/stdout

CMD /bin/bash
