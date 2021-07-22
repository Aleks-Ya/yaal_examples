#!/bin/bash

set -e

export KERBEROS_SHARED=/tmp/kerberos
#kadmin.local -q "xst -norandkey -k $KERBEROS_SHARED/hdfs.keytab hdfs/master.yaal.ru@HADOOPCLUSTER.LOCAL"
#kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab hdfs/slave1.yaal.ru@HADOOPCLUSTER.LOCAL"
#kadmin.local -q "ktadd -k $KERBEROS_SHARED/hdfs.keytab hdfs/slave2.yaal.ru@HADOOPCLUSTER.LOCAL"
cp "$KRB5_CONFIG" "$KERBEROS_SHARED"
#chmod a+r "$KERBEROS_SHARED"/hdfs.keytab #TODO use group permission
ls -l "$KERBEROS_SHARED"
#klist -k "$KERBEROS_SHARED/hdfs.keytab"
echo "KDC is ready."
bash
