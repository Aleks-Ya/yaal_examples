# Apache AirFlow Docker image

## Source
https://github.com/puckel/docker-airflow

## Run
UI: http://localhost:8080

### DAG folder is inside a container
```
docker run -d \
    --net bridge \
    --name airflow \
    -p 8080:8080 \
    puckel/docker-airflow \
    webserver
```

### DAG folder is outside a container
```
# Run WebServer
docker run -d \
  --env AIRFLOW__CORE__FERNET_KEY="HSOUU4w4_BcmkSMIE1a8VCrO3WCb3EbJw3OfH8IV1tM=" \
  --net bridge \
  --name airflow \
  --mount type=bind,source="$(pwd)"/dags,target=/usr/local/airflow/dags \
  -p 8080:8080 \
  puckel/docker-airflow \
  webserver

# Run scheduler
docker exec -t airflow airflow scheduler --daemon
```

## Attach with bash
"airflow" user:
`docker exec -it airflow bash`
"root" user:
`docker exec -it --user root airflow bash`

## Stop and remove the container
```
docker stop airflow
docker rm airflow
```

## Test a DAG file for errors
`docker exec -it airflow python /usr/local/airflow/dags/Varialbes.py`

## Build and run
`docker build --network default -t iablokov/airflow:1 .`
```
docker run -d \
  --env AIRFLOW__CORE__FERNET_KEY="HSOUU4w4_BcmkSMIE1a8VCrO3WCb3EbJw3OfH8IV1tM=" \
  --net bridge \
  --name airflow \
  --mount type=bind,source="$(pwd)"/dags,target=/usr/local/airflow/dags \
  -p 8080:8080 \
  iablokov/airflow:1 \
  webserver

  # Run scheduler
  docker exec -t airflow airflow scheduler --daemon
  ```
