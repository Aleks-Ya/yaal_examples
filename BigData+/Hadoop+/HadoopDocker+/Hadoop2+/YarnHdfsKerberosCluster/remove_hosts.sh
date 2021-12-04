#!/bin/bash

# Remove hostnames of Docker containers from /etc/hosts.
# Requires root privileges.

set -e

hosts_file=/etc/hosts

host_1=yarn-hdfs-kerberos-master.yarn.yaal.ru
host_2=yarn-hdfs-kerberos-slave1.yarn.yaal.ru
host_3=yarn-hdfs-kerberos-slave2.yarn.yaal.ru
host_4=yarn-hdfs-kerberos-kdc.yarn.yaal.ru

sed -i "/${host_1}/d" ${hosts_file}
sed -i "/${host_2}/d" ${hosts_file}
sed -i "/${host_3}/d" ${hosts_file}
sed -i "/${host_4}/d" ${hosts_file}
