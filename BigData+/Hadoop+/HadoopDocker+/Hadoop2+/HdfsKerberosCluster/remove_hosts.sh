#!/bin/bash

# Remove hostnames of Docker containers from /etc/hosts.
# Requires root privileges.

set -e

hosts_file=/etc/hosts

host_1=hdfs-master.hdfs.yaal.ru
host_2=hdfs-slave1.hdfs.yaal.ru
host_3=hdfs-slave2.hdfs.yaal.ru
host_4=hdfs-kdc.hdfs.yaal.ru

sed -i "/${host_1}/d" ${hosts_file}
sed -i "/${host_2}/d" ${hosts_file}
sed -i "/${host_3}/d" ${hosts_file}
sed -i "/${host_4}/d" ${hosts_file}
