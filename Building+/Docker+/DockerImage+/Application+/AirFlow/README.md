# Apache AirFlow Docker image

## Source
https://github.com/puckel/docker-airflow

## UI
http://localhost:8080

## Standard image
Downside: require to start scheduler manually.

### DAG folder is inside a container
```
docker run -d \
    --net bridge \
    --name airflow \
    -p 8080:8080 \
    puckel/docker-airflow:1.9.0-4 \
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
  puckel/docker-airflow:1.9.0-4 \
  webserver

# Run scheduler
docker exec -t airflow airflow scheduler --daemon
```

### Stop and remove the container
```
docker stop airflow
docker rm airflow
```

## Improved container
Improvements:
1. Run scheduler automatically
1. Install debug Linux packages
1. Set a fernet key
1. Add "user" variable
1. (not work) Stop and start container
1. Install PyCharm (and IntelliJ Idea) debug library.

### Build and run
```
# Build
docker build --network default -t iablokov/airflow:1 -f docker/Dockerfile docker

# Run
docker run -it --rm \
  --net host \
  --name airflow \
  --mount type=bind,source="$(pwd)"/dags,target=/usr/local/airflow/dags \
  iablokov/airflow:1

# Stop and start
docker stop airflow
docker start airflow

# Remove
docker rm airflow
```

## Information
### Attach with bash
```
# "airflow" user
docker exec -it airflow bash

# "root" user
docker exec -it -u root airflow bash
```

### Test a DAG file for errors
`docker exec -it airflow python /usr/local/airflow/dags/Varialbes.py`
