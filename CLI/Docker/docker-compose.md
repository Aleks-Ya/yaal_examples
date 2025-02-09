# docker-compose CLI

Docs: [Docker Compose CLI reference](https://docs.docker.com/compose/reference/)

## Actual (using Docker Desktop for Linux)
Show version: `docker compose version`
Run with custom compose file: `docker-compose -f my.yml`

## Obsolete
### Build, start, stop

Build project: `docker-compose build`
Start project: `docker-compose up`
Start project with force rebuild: `docker-compose up --build`
Stop project: `docker-compose down`
Stop project and remove volumes: `docker-compose down -v`

### Logs

Show logs: `docker-compose logs <service_name>`
Follow logs: `docker-compose logs -f <service_name>`
Show logs for all services and follow: `docker-compose logs -f`
