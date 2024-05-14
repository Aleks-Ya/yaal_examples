# Docker CLI: network

List all networks: `docker network ls`
Show network details: `docker network inspect bridge`
Create network: `docker network create --driver bridge --subnet 192.168.100.0/24 --ip-range 192.168.100.0/24 my-bridge-network`
Get network IP address: `docker network inspect my-network --format='{{(index .IPAM.Config 0).Gateway}}'`
Delete a network: `docker network rm my-network`
