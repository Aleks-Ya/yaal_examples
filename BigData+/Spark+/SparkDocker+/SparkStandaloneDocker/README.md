# Spark Cluster on Standalone Spark instances

## Build
`./build.sh`

## Start cluster
1. Start: `docker-compose up`
2. Publis hosts: `sudo ./update_hosts.sh`
3. Open: http://spark-standalone-cluster-master:8080

## Connect by Spark Shell
1. Run Spark Shell: `spark-shell --master spark://spark-standalone-cluster-master:7077`
2. Execute app: `sc.parallelize(Seq(1, 2, 3)).collect()`

## Stop cluster
`docker-compose stop`

## Delete cluster
`docker-compose down`
