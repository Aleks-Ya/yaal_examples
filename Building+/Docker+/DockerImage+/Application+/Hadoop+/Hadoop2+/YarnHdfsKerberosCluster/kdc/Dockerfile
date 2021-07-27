FROM alpine-updated:3

RUN apk add krb5-server krb5

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=/etc/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf
ENV ROOT_PRINCIPAL=root/admin@HADOOPCLUSTER.LOCAL
ENV SSERVER_PRINCIPAL=sserver/yarn-sserver.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV SCLIENT_PRINCIPAL=sclient@HADOOPCLUSTER.LOCAL
ENV HADOOP_PRINCIPAL=hadoop/admin@HADOOPCLUSTER.LOCAL
ENV NN_MASTER_PRINCIPAL=nn/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV DN_MASTER_PRINCIPAL=dn/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV DN_SLAVE1_PRINCIPAL=dn/yarn-slave1.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV DN_SLAVE2_PRINCIPAL=dn/yarn-slave2.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV HOST_MASTER_PRINCIPAL=host/yarn-master.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV HOST_SLAVE1_PRINCIPAL=host/yarn-slave1.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV HOST_SLAVE2_PRINCIPAL=host/yarn-slave2.yarn.yaal.ru@HADOOPCLUSTER.LOCAL
ENV CLIENT_PRINCIPAL=client@HADOOPCLUSTER.LOCAL
ENV KRB5_TRACE=/dev/stdout

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD entrypoint.sh /tmp/entrypoint.sh

RUN kdb5_util create -r HADOOPCLUSTER.LOCAL -s -P the_db_master_pass \
    && kadmin.local -q "add_policy -maxlife 180days policy180" \
    && kadmin.local -q "addprinc -pw the_hadoop_pass      -policy policy180 $HADOOP_PRINCIPAL"      -p $ROOT_PRINCIPAL   \
    && kadmin.local -q "addprinc -pw the_nn_master_pass   -policy policy180 $NN_MASTER_PRINCIPAL"   -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_dn_master_pass   -policy policy180 $DN_MASTER_PRINCIPAL"   -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_dn_slave1_pass   -policy policy180 $DN_SLAVE1_PRINCIPAL"   -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_dn_slave2_pass   -policy policy180 $DN_SLAVE2_PRINCIPAL"   -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_host_master_pass -policy policy180 $HOST_MASTER_PRINCIPAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_host_slave1_pass -policy policy180 $HOST_SLAVE1_PRINCIPAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_host_slave2_pass -policy policy180 $HOST_SLAVE2_PRINCIPAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_client_pass      -policy policy180 $CLIENT_PRINCIPAL"      -p $ROOT_PRINCIPAL   \
    && kadmin.local -q "addprinc -pw the_sserver_pass     -policy policy180 $SSERVER_PRINCIPAL"     -p $ROOT_PRINCIPAL   \
    && kadmin.local -q "addprinc -pw the_sclient_pass     -policy policy180 $SCLIENT_PRINCIPAL"     -p $ROOT_PRINCIPAL

EXPOSE 88

CMD krb5kdc && /tmp/entrypoint.sh
