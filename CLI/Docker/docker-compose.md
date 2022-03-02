docker-compose CLI

Docs: [Docker Compose CLI reference](https://docs.docker.com/compose/reference/)

Build, start, stop

Build project: docker-compose build
Start project: docker-compose up
Start project with force rebuild: docker-compose up --build
Stop project: docker-compose down

Logs

Show logs: docker-compose logs <service_name>
Follow logs: docker-compose logs -f <service_name>
Show logs for all services and follow: docker-compose logs -f

