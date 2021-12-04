#!/bin/bash

# Add hostnames of Docker containers to /etc/hosts.
# Requires root privileges.

set -e

./remove_hosts.sh

container_1=yarn-hdfs-master
container_2=yarn-hdfs-slave1
container_3=yarn-hdfs-slave2

ip_1=$(docker exec ${container_1} hostname -I)
ip_2=$(docker exec ${container_2} hostname -I)
ip_3=$(docker exec ${container_3} hostname -I)

hosts_file=/etc/hosts

host_1=${container_1}.yarn.yaal.ru
host_2=${container_2}.yarn.yaal.ru
host_3=${container_3}.yarn.yaal.ru

{
  echo "${ip_1}    ${host_1}"
  echo "${ip_2}    ${host_2}"
  echo "${ip_3}    ${host_3}"
} >> ${hosts_file}
