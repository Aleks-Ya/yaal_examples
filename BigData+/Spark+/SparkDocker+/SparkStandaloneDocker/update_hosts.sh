#!/bin/bash

# Add hostnames of Docker containers to /etc/hosts.
# Requires root privileges.

set -e

./remove_hosts.sh

container_1=spark-standalone-cluster-master
container_2=spark-standalone-cluster-slave-1
container_3=spark-standalone-cluster-slave-2

ip_1=$(docker exec ${container_1} hostname -I)
ip_2=$(docker exec ${container_2} hostname -I)
ip_3=$(docker exec ${container_3} hostname -I)

hosts_file=/etc/hosts

host_1=spark-standalone-cluster-master
host_2=spark-standalone-cluster-slave-1
host_3=spark-standalone-cluster-slave-2

echo "${ip_1}    ${host_1}" >> ${hosts_file}
echo "${ip_2}    ${host_2}" >> ${hosts_file}
echo "${ip_3}    ${host_3}" >> ${hosts_file}
