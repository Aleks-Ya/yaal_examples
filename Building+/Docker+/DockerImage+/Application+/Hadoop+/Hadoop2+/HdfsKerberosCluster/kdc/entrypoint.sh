#!/bin/bash
set -e

export KERBEROS_SHARED=/tmp/kerberos
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sserver.keytab $SSERVER_PRINCIPAL"
kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sclient.keytab $SCLIENT_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/hdfs.keytab hdfs/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab hdfs/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab hdfs/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab HOST/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab HOST/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab HOST/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"

cp "$KRB5_CONFIG" "$KERBEROS_SHARED"
chmod a+r "$KERBEROS_SHARED"/hdfs.keytab #TODO use group permission
ls -l "$KERBEROS_SHARED"

klist -kte "$KERBEROS_SHARED/hdfs.keytab"
klist -kte "$KERBEROS_SHARED/sserver.keytab"
klist -kte "$KERBEROS_SHARED/sclient.keytab"

bash
