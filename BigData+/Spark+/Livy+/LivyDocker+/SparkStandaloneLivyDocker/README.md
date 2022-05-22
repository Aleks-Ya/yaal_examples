# Spark Cluster on Standalone Spark instances (with Livy)

## Build
`./build.sh`

### Debug build
#### `spark-distr`
Build: `docker build --build-arg SPARK_VERSION=3.2.1 --target spark-distr -t spark-distr-tmp context`
Check ditributive files: `docker run --rm spark-distr-tmp ls /spark-distr`
#### `livy-distr`
Build: `docker build --build-arg SPARK_VERSION=3.2.1 --build-arg LIVY_VERSION=0.7.1 --target livy-distr -t livy-distr-tmp context`
Check ditributive files: `docker run --rm livy-distr-tmp ls /livy-distr`

## Start cluster
1. Start: `docker-compose up`
2. Publis hosts: `sudo ./update_hosts.sh`
3. Open: http://spark-standalone-cluster-master:8080

## Web
1. Spark UI: http://spark-standalone-cluster-master:8080
2. Spark History Server: http://spark-standalone-cluster-master:18080
3. Livy UI: http://spark-standalone-cluster-livy:8998/ui

## Logs
1. Livy: `docker exec spark-standalone-cluster-livy less /opt/livy/logs/livy-livy-server.out`

## Connect by Spark Shell
1. Run Spark Shell: `spark-shell --master spark://spark-standalone-cluster-master:7077`
2. Execute app: `sc.parallelize(Seq(1, 2, 3)).collect()`

## Stop cluster
`docker-compose stop`

## Delete cluster
`docker-compose down`


## TODO
1. SPARK_VERSION and LIVY_VERSION env vars are defined twice: in `.env` and in `build.sh`. Remove this duplication.
