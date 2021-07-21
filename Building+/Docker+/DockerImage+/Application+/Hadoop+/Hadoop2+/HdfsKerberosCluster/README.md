# Hadoop HDFS cluster with Kerberos (no HA)

## Build images
`./build.sh`

## Run cluster
1. Start cluster: `./run_cluster.sh`
2. Update hosts file: `sudo ./update_hosts.sh`
3. Check: http://master-service:50070

## Hadoop CLI
Run Bash with Hadoop CLI available: `./run_cli.sh`  
Example of a command: `hdfs dfs -ls /`

## Stop cluster
`docker-compose stop`

## Web UI
- HDFS
    - Active Name Node UI: http://master-service:50070
    - Data Node 1 UI: http://master-service:50075
    - Data Node 2 UI: http://slave-service-1:50075
    - Data Node 3 UI: http://slave-service-2:50075
