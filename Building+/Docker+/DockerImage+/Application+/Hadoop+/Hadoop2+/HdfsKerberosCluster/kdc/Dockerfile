FROM alpine-updated:3

RUN apk add krb5-server krb5

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=${LOCALSTATEDIR}/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf
ENV ROOT_PRINCIPAL=root/admin@HADOOPCLUSTER.LOCAL
ENV SSERVER_PRINCIPAL=sserver/hdfs-sserver.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL
ENV SCLIENT_PRINCIPAL=sclient@HADOOPCLUSTER.LOCAL
ENV HADOOP_PRINCIPAL=hadoop/admin@HADOOPCLUSTER.LOCAL
ENV KRB5_TRACE=/dev/stdout

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD entrypoint.sh /tmp/entrypoint.sh

RUN kdb5_util create -r HADOOPCLUSTER.LOCAL -s -P the_db_master_pass \
    && kadmin.local -q "add_policy -maxlife 180days policy180" \
    && kadmin.local -q "addprinc -pw the_sserver_pass     -policy policy180 $SSERVER_PRINCIPAL"          -p $ROOT_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_sclient_pass     -policy policy180 $SCLIENT_PRINCIPAL"          -p $ROOT_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hadoop_pass      -policy policy180 $HADOOP_PRINCIPAL"           -p $ROOT_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_client_pass      -policy policy180 client@HADOOPCLUSTER.LOCAL"  -p $ROOT_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_master_pass -policy policy180 hdfs/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave1_pass -policy policy180 hdfs/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave2_pass -policy policy180 hdfs/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_master_pass -policy policy180 HOST/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave1_pass -policy policy180 HOST/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave2_pass -policy policy180 HOST/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL" -p $HADOOP_PRINCIPAL


EXPOSE 88

CMD krb5kdc && /tmp/entrypoint.sh
