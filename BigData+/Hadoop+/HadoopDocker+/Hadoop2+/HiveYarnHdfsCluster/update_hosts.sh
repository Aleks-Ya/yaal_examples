#!/bin/bash

# Add hostnames of Docker containers to /etc/hosts.
# Requires root privileges.

set -e

./remove_hosts.sh

container_1=hive-yarn-hdfs-master
container_2=hive-yarn-hdfs-slave-1
container_3=hive-yarn-hdfs-slave-2
container_4=hive-yarn-hdfs-postgres

ip_1=$(docker exec ${container_1} hostname -I)
ip_2=$(docker exec ${container_2} hostname -I)
ip_3=$(docker exec ${container_3} hostname -I)
ip_4=$(docker exec ${container_4} hostname -I)

hosts_file=/etc/hosts

host_1=master-service
host_2=slave-service-1
host_3=slave-service-2
host_4=postgres

{
  echo "${ip_1}    ${host_1}"
  echo "${ip_2}    ${host_2}"
  echo "${ip_3}    ${host_3}"
  echo "${ip_4}    ${host_4}"
} >> ${hosts_file}
