# OpenSearch

DockerHub: https://hub.docker.com/r/opensearchproject/opensearch

Default credentials: `admin`/`admin`

## Run single node (OpenSearch only)
1. Start: `docker run -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" --name opensearch-node -d opensearchproject/opensearch:latest`
2. Check state: `curl https://localhost:9200 -ku admin:admin`

## Run OpenSearch + Dashboard
1. Start: `docker-compose up -d`
2. Check state: `curl https://localhost:9200 -ku admin:admin`
3. Open http://localhost:5601
