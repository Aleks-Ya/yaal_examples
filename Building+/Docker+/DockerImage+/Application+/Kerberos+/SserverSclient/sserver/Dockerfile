FROM alpine-updated:3

RUN apk add krb5-server

ENV KERBEROS_SHARED=/tmp/kerberos
ENV KRB5_CONFIG="$KERBEROS_SHARED/krb5.conf"
ENV KEYTAB="$KERBEROS_SHARED/sserver.keytab"
ENV KRB5_TRACE=/dev/stdout

ADD run_sserver.sh /tmp/

CMD /bin/bash
