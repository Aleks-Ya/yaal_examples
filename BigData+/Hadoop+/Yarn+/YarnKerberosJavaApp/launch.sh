#!/bin/bash

set -e

echo "Authenticating in Kerberos..."
kinit -kt /tmp/kerberos/client.keytab client@HADOOPCLUSTER.LOCAL

echo "Putting the app jar into HDFS..."
hdfs dfs -rm -r -f /apps
hdfs dfs -mkdir -p /apps
hdfs dfs -copyFromLocal /tmp/YarnKerberosJavaApp.jar /apps/YarnKerberosJavaApp.jar

echo "Executing the app..."
yarn jar /tmp/YarnKerberosJavaApp.jar yarn.Client
