# Apache AirFlow Docker image

## Source
https://github.com/puckel/docker-airflow

## Run bash
No DNS:
`docker run --net bridge --name airflow -d -p 8080:8080 puckel/docker-airflow webserver`
With DNS:
`docker run --net bridge --name airflow -d -p 8080:8080 --dns 8.8.8.8 --dns 10.66.0.6 puckel/docker-airflow webserver`

## Attach with bash
`docker exec -it --user root airflow bash`
