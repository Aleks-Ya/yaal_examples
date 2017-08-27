# Getting Started

## Sources
Example from Documentation:
https://docs.docker.com/compose/gettingstarted/#step-4-build-and-run-your-app-with-compose

## Run application
`docker-compose up`

In background (detached mode): `docker-compose up -d`

## Show run services
`docker-compose ps`

## See environment variables
`docker-compose run web env`

## Stop services
`docker-compose stop`

## Full stop: remove containers and volumes
`docker-compose down --volumes`
