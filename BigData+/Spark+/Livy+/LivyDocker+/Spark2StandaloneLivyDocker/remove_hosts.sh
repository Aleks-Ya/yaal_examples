#!/bin/bash

# Remove hostnames of Docker containers from /etc/hosts.
# Requires root privileges.

set -e

hosts_file=/etc/hosts

host_1=spark2-standalone-livy-cluster-master
host_2=spark2-standalone-livy-cluster-slave-1
host_3=spark2-standalone-livy-cluster-slave-2
host_4=spark2-standalone-livy-cluster-livy

sed -i "/${host_1}/d" ${hosts_file}
sed -i "/${host_2}/d" ${hosts_file}
sed -i "/${host_3}/d" ${hosts_file}
sed -i "/${host_4}/d" ${hosts_file}
