FROM alpine-updated:3

RUN apk add krb5-server krb5

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=${LOCALSTATEDIR}/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf
ENV ROOT_PRINCIPAL=root/admin@HADOOPCLUSTER.LOCAL
ENV SSERVER_PRINCIPAL=sserver/hdfs-sserver.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL
ENV SCLIENT_PRINCIPAL=sclient@HADOOPCLUSTER.LOCAL
ENV KRB5_TRACE=/dev/stdout

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD entrypoint.sh /tmp/entrypoint.sh

RUN kdb5_util create -r HADOOPCLUSTER.LOCAL -s -P the_db_master_pass
RUN kadmin.local -q "add_policy -maxlife 180days policy180"
RUN kadmin.local -q "addprinc -pw the_sserver_pass -policy policy180 $SSERVER_PRINCIPAL" -p $ROOT_PRINCIPAL
RUN kadmin.local -q "addprinc -pw the_sclient_pass -policy policy180 $SCLIENT_PRINCIPAL" -p $ROOT_PRINCIPAL

CMD krb5kdc && /tmp/entrypoint.sh
