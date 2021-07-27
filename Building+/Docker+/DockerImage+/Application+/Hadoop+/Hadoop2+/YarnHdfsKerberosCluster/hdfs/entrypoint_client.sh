#!/bin/bash

set -e

echo "HADOOP_PREFIX=$HADOOP_PREFIX"
echo "HADOOP_CONF_DIR=$HADOOP_CONF_DIR"
export KRB5_TRACE=/dev/stdout
export KRB5CCNAME=/tmp/krb5cc
export KERBEROS_SHARED=/tmp/kerberos
export KEYTAB="$KERBEROS_SHARED/hdfs.keytab"

echo "kinit..."
kinit -kt /tmp/kerberos/client.keytab client@HADOOPCLUSTER.LOCAL
echo "kinit done"

echo "klist..."
klist
echo "klist done"

sleep 15s

echo "report..."
hdfs dfsadmin -report
echo "report done"

echo "hdfs ls..."
hdfs dfs -ls /
echo "hdfs ls done"

trap stop $SIGINT $SIGTERM
echo 'Press Ctrl-C or execute "docker-compose stop" to exit'
while true; do :; done
