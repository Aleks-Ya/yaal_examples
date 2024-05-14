# Spark Cluster on Standalone Spark instances

## Build
`./build.sh`

## Start cluster
1. Start: `./run_cluster.sh`
2. Publish hosts: `sudo ./update_hosts.sh`
3. Open: http://spark-standalone-cluster-master:8080

## Test Master services
1. Web UI: `telnet spark-standalone-cluster-master 8080`
2. Port: `telnet spark-standalone-cluster-master 7077`
3. Spark Connect: `telnet spark-standalone-cluster-master 15002`
4. History Server: `telnet spark-standalone-cluster-master 18080`

## Web
1. Spark UI: http://spark-standalone-cluster-master:8080
2. Spark History Server: http://spark-standalone-cluster-master:18080

## See logs
1. Master
    1. Master: `docker exec -it spark-standalone-cluster-master tail -f /opt/spark/logs/spark-spark-org.apache.spark.deploy.master.Master-1-spark-standalone-cluster-master.out`
    2. Spark Connect: `docker exec -it spark-standalone-cluster-master tail -f /opt/spark/logs/spark-spark-org.apache.spark.sql.connect.service.SparkConnectServer-1-spark-standalone-cluster-master.out`
    3. History Server: `docker exec -it spark-standalone-cluster-master tail -f /opt/spark/logs/spark-spark-org.apache.spark.deploy.history.HistoryServer-1-spark-standalone-cluster-master.out`
2. Slave 1
   1. Worker 1: `docker exec -it spark-standalone-cluster-slave-1 tail -f /opt/spark/logs/spark-spark-org.apache.spark.deploy.worker.Worker-1-spark-standalone-cluster-slave-1.out`
3. Slave 2
   1. Worker 2: `docker exec -it spark-standalone-cluster-slave-2 tail -f /opt/spark/logs/spark-spark-org.apache.spark.deploy.worker.Worker-1-spark-standalone-cluster-slave-2.out`

## Connect by Spark Shell
### From a container
1. Run Spark Shell:
   ```shell
   docker run --rm -it --network spark-standalone-cluster-network spark-standalone \
      spark-shell \
      --total-executor-cores 1 \
      --master spark://spark-standalone-cluster-master:7077
   ```
2. Execute app: `sc.parallelize(Seq(1, 2, 3)).sum()`
### From the host machine
1. Run Spark Shell: `spark-shell --total-executor-cores 1 --master spark://spark-standalone-cluster-master:7077`
2. Execute app: `sc.parallelize(Seq(1, 2, 3)).sum()`

## Spark Connect
REPL (doesn't work): `spark-connect-repl --host spark-standalone-cluster-master --port 15002`
Standalone app: see `BigData+/Spark+/SparkScala+/Spark3+/Core+/Spark3Connect`

## Stop cluster
`docker compose stop`

## Delete cluster
`docker compose down`

## Errors
### Initial job has not accepted any resources
Command: `sc.parallelize(Seq(1, 2, 3)).sum()`
Message: `WARN TaskSchedulerImpl: Initial job has not accepted any resources; check your cluster UI to ensure that workers are registered and have sufficient resources`
Cause: UWF (Linux firewall) is enabled
Solution: 
