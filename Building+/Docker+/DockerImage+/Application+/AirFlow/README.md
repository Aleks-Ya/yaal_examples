# Apache AirFlow Docker image

## Source
https://github.com/puckel/docker-airflow

## Run
### DAG folder is inside a container
No DNS:
```
docker run -d \
    --net bridge \
    --name airflow \
    -p 8080:8080 \
    puckel/docker-airflow \
    webserver
```

With DNS:
```docker run -d \
  --net bridge \
  --name airflow \
  -p 8080:8080 \
  --dns 8.8.8.8 \
  --dns 10.66.0.6 \
  puckel/docker-airflow \
  webserver
```

### DAG folder is outside a container
No DNS:
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
`docker stop airflow`
`docker rm airflow`

## Test a DAG file
`docker exec -it airflow python /usr/local/airflow/dags/Varialbes.py`
