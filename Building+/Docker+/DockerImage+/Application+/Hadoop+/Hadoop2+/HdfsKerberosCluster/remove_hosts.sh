#!/bin/bash

# Remove hostnames of Docker containers from /etc/hosts.
# Requires root privileges.

set -e

hosts_file=/etc/hosts

host_1=master-service
host_2=slave-service1
host_3=slave-service2

sed -i "/${host_1}/d" ${hosts_file}
sed -i "/${host_2}/d" ${hosts_file}
sed -i "/${host_3}/d" ${hosts_file}
