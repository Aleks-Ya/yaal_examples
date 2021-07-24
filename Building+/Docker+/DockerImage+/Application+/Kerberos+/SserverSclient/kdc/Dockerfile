# Run KDC (Key Destribution Center)

FROM debian:stable

RUN apt-get update && apt-get install -y nano net-tools telnet less htop
RUN apt-get install -y krb5-admin-server krb5-config

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=${LOCALSTATEDIR}/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf
ENV ROOT_PRINCIPAL=root/admin@LOCAL.REALM
ENV SSERVER_PRINCIPAL=sserver/sserver.kerberos.yaal.ru@LOCAL.REALM
ENV SCLIENT_PRINCIPAL=sclient@LOCAL.REALM
ENV KRB5_TRACE=/dev/stdout

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD entrypoint.sh /tmp/entrypoint.sh

RUN kdb5_util create -r LOCAL.REALM -s -P the_db_master_pass
RUN kadmin.local -q "addprinc -pw the_sserver_pass $SSERVER_PRINCIPAL" -p $ROOT_PRINCIPAL
RUN kadmin.local -q "addprinc -pw the_sclient_pass $SCLIENT_PRINCIPAL" -p $ROOT_PRINCIPAL

EXPOSE 749
EXPOSE 750
EXPOSE 88

CMD krb5kdc && /tmp/entrypoint.sh
