#!/bin/bash
set -e

export KERBEROS_SHARED=/tmp/kerberos
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sserver.keytab $SSERVER_PRINCIPAL"
kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/sclient.keytab $SCLIENT_PRINCIPAL"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/nn.keytab nn/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/nn.keytab dn/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/nn.keytab host/hdfs-master.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/dn2.keytab dn/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/dn2.keytab host/hdfs-slave1.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/dn3.keytab dn/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"
kadmin.local -q "ktadd -k $KERBEROS_SHARED/dn3.keytab host/hdfs-slave2.hdfs.yaal.ru@HADOOPCLUSTER.LOCAL"

kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/client.keytab client@HADOOPCLUSTER.LOCAL"

cp "$KRB5_CONFIG" "$KERBEROS_SHARED"
chmod a+r "$KERBEROS_SHARED"/*.keytab #TODO use group permission
ls -l "$KERBEROS_SHARED"

klist -kte "$KERBEROS_SHARED/nn.keytab"
klist -kte "$KERBEROS_SHARED/dn2.keytab"
klist -kte "$KERBEROS_SHARED/dn3.keytab"
klist -kte "$KERBEROS_SHARED/sserver.keytab"
klist -kte "$KERBEROS_SHARED/sclient.keytab"
klist -kte "$KERBEROS_SHARED/client.keytab"

bash
