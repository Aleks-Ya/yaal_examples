# Run Spark in Docker

Docker Hub: https://hub.docker.com/r/singularities/spark/
GitHub:  https://github.com/SingularitiesCR/spark-docker

Spark Master Web UI: http://localhost:8080/
REST API: http://localhost:6066/
HDFS UI: http://localhost:50070
Spark Master: spark://master:7077

Master IP address: docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' spark_master_1

## Run
`docker-compose up`

Run Spark Shell:
`docker exec -it spark_master_1 spark-shell --master spark://master:7077`
