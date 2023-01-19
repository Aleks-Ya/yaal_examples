#!/bin/bash

# Add hostnames of Docker containers to /etc/hosts.
# Requires root privileges.

set -e

./remove_hosts.sh

container_1=spark2-standalone-livy-cluster-master
container_2=spark2-standalone-livy-cluster-slave-1
container_3=spark2-standalone-livy-cluster-slave-2
container_4=spark2-standalone-livy-cluster-livy

ip_1=$(docker exec ${container_1} hostname -I)
ip_2=$(docker exec ${container_2} hostname -I)
ip_3=$(docker exec ${container_3} hostname -I)
ip_4=$(docker exec ${container_4} hostname -I)

hosts_file=/etc/hosts

host_1=spark2-standalone-livy-cluster-master
host_2=spark2-standalone-livy-cluster-slave-1
host_3=spark2-standalone-livy-cluster-slave-2
host_4=spark2-standalone-livy-cluster-livy

echo "${ip_1}    ${host_1}" >> ${hosts_file}
echo "${ip_2}    ${host_2}" >> ${hosts_file}
echo "${ip_3}    ${host_3}" >> ${hosts_file}
echo "${ip_4}    ${host_4}" >> ${hosts_file}
