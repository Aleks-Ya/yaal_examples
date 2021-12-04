#!/bin/bash

# Add hostnames of Docker containers to /etc/hosts.
# Requires root privileges.

set -e

./remove_hosts.sh

container_1=hdfs-master
container_2=hdfs-slave1
container_3=hdfs-slave2
container_4=hdfs-kdc

ip_1=$(docker exec ${container_1} hostname -I)
ip_2=$(docker exec ${container_2} hostname -I)
ip_3=$(docker exec ${container_3} hostname -I)
ip_4=$(docker exec ${container_4} hostname -i)

hosts_file=/etc/hosts

host_1=${container_1}.hdfs.yaal.ru
host_2=${container_2}.hdfs.yaal.ru
host_3=${container_3}.hdfs.yaal.ru
host_4=${container_4}.hdfs.yaal.ru

{
  echo "${ip_1}    ${host_1}"
  echo "${ip_2}    ${host_2}"
  echo "${ip_3}    ${host_3}"
  echo "${ip_4}    ${host_4}"
} >> ${hosts_file}
