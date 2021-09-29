FROM alpine-updated:3

RUN apk add krb5-server krb5

ENV KERBEROS_SHARED=/tmp/kerberos
ENV KRB5_CONFIG="$KERBEROS_SHARED/krb5.conf"
ENV KEYTAB="$KERBEROS_SHARED/sclient.keytab"
ENV KRB5CCNAME=/tmp/krb5cc
ENV KRB5_TRACE=/dev/stdout

ADD run_sclient.sh /tmp/

CMD /bin/bash
