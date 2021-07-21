FROM debian:stable

RUN apt-get update && apt-get install -y nano net-tools telnet less htop
RUN apt-get install -y krb5-admin-server krb5-config

ENV LOCALSTATEDIR=/var
ENV KRB5_CONFIG=${LOCALSTATEDIR}/krb5.conf
ENV KRB5_KDC_PROFILE=${LOCALSTATEDIR}/kdc.conf

ADD krb5.conf ${KRB5_CONFIG}
ADD kdc.conf ${KRB5_KDC_PROFILE}
ADD kadm5.acl ${LOCALSTATEDIR}/kadm5.acl

RUN kdb5_util create -r HADOOPCLUSTER.LOCAL -s -P the_db_master_pass \
    && kadmin.local -q "addprinc -pw the_hadoop_pass hadoop/admin@HADOOPCLUSTER.LOCAL" -p root/admin@HADOOPCLUSTER.LOCAL \
    && kadmin.local -q "addprinc -pw the_hdfs_master_pass hdfs/master.yaal.ru@HADOOPCLUSTER.LOCAL" -p hadoop/admin@HADOOPCLUSTER.LOCAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave1_pass hdfs/slave1.yaal.ru@HADOOPCLUSTER.LOCAL" -p hadoop/admin@HADOOPCLUSTER.LOCAL \
    && kadmin.local -q "addprinc -pw the_hdfs_slave2_pass hdfs/slave2.yaal.ru@HADOOPCLUSTER.LOCAL" -p hadoop/admin@HADOOPCLUSTER.LOCAL \
    && kadmin.local -q "xst -norandkey -k /tmp/hdfs.keytab hdfs/master.yaal.ru@HADOOPCLUSTER.LOCAL" \
    && kadmin.local -q "ktadd -k /tmp/hdfs.keytab hdfs/slave1.yaal.ru@HADOOPCLUSTER.LOCAL" \
    && kadmin.local -q "ktadd -k /tmp/hdfs.keytab hdfs/slave2.yaal.ru@HADOOPCLUSTER.LOCAL"

EXPOSE 749
EXPOSE 750
EXPOSE 88

CMD krb5kdc && kadmind && /bin/bash
