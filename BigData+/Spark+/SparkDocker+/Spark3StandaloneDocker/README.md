# Spark Cluster on Standalone Spark instances

## Build
`./build.sh`

## Start cluster
1. Start: `./run_cluster.sh`
2. Publis hosts: `sudo ./update_hosts.sh`
3. Open: http://spark-standalone-cluster-master:8080

## Web
1. Spark UI: http://spark-standalone-cluster-master:8080
2. Spark History Server: http://spark-standalone-cluster-master:18080

## See logs
1. Master
    1. Master: `docker exec -it spark-standalone-cluster-master less /opt/spark/logs/spark-spark-org.apache.spark.deploy.master.Master-1-spark-standalone-cluster-master.out`
    2. History Server: `docker exec -it spark-standalone-cluster-master less /opt/spark/logs/spark-spark-org.apache.spark.deploy.history.HistoryServer-1-spark-standalone-cluster-master.out`
2. Slave 1
   1. Worker 1: `docker exec -it spark-standalone-cluster-slave-1 less /opt/spark/logs/spark-spark-org.apache.spark.deploy.worker.Worker-1-spark-standalone-cluster-slave-1.out`
3. Slave 2
   1. Worker 2: `docker exec -it spark-standalone-cluster-slave-2 less /opt/spark/logs/spark-spark-org.apache.spark.deploy.worker.Worker-1-spark-standalone-cluster-slave-2.out`

## Connect by Spark Shell
1. Run Spark Shell: `docker run --rm --network spark-standalone-cluster-network -it spark-standalone spark-shell --master spark://spark-standalone-cluster-master:7077`
2. Execute app: `sc.parallelize(Seq(1, 2, 3)).collect()`

## Stop cluster
`docker compose stop`

## Delete cluster
`docker compose down`
