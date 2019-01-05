# Hadoop cluster (HDFS, YARN, Spark)

## TODO
1. Setup active failover (https://hadoop.apache.org/docs/r2.9.2/hadoop-project-dist/hadoop-hdfs/HDFSHighAvailabilityWithQJM.html)
1. Add Docker HEALTHCHECK
1. Use IP addressed instead of hostnames (so links in Web UI will work)
1. Publish to Docker Hub

## Build images
`./build.sh`

## Run cluster
`./run_cluster.sh`

## Hadoop CLI
Run Bash with Hadoop CLI available: `./run_cli.sh`

Examples of commands:
- See HDFS Data Node statuses: `hdfs dfsadmin -report`
- See HDFS Name Node statuses: `hdfs haadmin -getAllServiceState`
- See YARN nodes: `yarn node -list -all`
- Run example YARN application: `${HADOOP_PREFIX}/run_yarn_example.sh`
- Run example Spark application: `${HADOOP_PREFIX}/run_spark_example.sh`

## Remove cluster
`docker-compose down`

## Web UI
- HDFS
  - Active Name Node UI: http://localhost:50070
  - Standby Name Node UI: http://localhost:51070
  - Data Node 1 UI: http://localhost:50075
  - Data Node 2 UI: http://localhost:51075
  - Data Node 3 UI: http://localhost:52075
- YARN
  - Resource Manager Web UI: http://localhost:8088
  - Node Manager 1 Web UI: http://localhost:8042
  - Node Manager 2 Web UI: http://localhost:8142
  - Node Manager 3 Web UI: http://localhost:8242
