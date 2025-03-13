/home/aleks/pr/home/yaal_examples/Building+/Docker+/DockerImage+/Application+/OpenSearch# OpenSearch

DockerHub: https://hub.docker.com/r/opensearchproject/opensearch

Default credentials: `admin`/`admin`

## Run single node (OpenSearch only)
1. Start: 
```shell
docker run --rm -p 9200:9200 -p 9600:9600 --name opensearch \
  -e "discovery.type=single-node" \
  -e "OPENSEARCH_INITIAL_ADMIN_PASSWORD=Apas@2xuV" opensearchproject/opensearch:latest
```
2. Check state: `curl https://localhost:9200 -ku admin:Apas@2xuV`

## Run OpenSearch + Dashboard
1. Start: `docker compose up`
2. Check state: `curl https://localhost:9200 -ku admin:admin`
3. Open http://localhost:5601

## REST API examples
See Postman `OpenSearch Docker` collection in `Personal` workspace

## Errors
### The bulk request must be terminated by a newline
Command: `curl -XPOST https://localhost:9200/bank/_bulk -ku admin:admin -d @accounts.json -H "Content-Type: application/x-ndjson"`
Message: `{"error":{"root_cause":[{"type":"illegal_argument_exception","reason":"The bulk request must be terminated by a newline [\\n]"}],"type":"illegal_argument_exception","reason":"The bulk request must be terminated by a newline [\\n]"},"status":400}`
Despite the `accounts.json` is formatted correctly (has one empty line in the end, has UTF-8 encoding, has Linux lineendings).
Cause: I shorten `--data-binary` parameter to `-d`, but it means `--data`.
Solution: replace `-d` with `--data-binary`, correct command: `curl -XPOST https://localhost:9200/bank/_bulk -ku admin:admin --data-binary @accounts.json -H "Content-Type: application/x-ndjson"`

### Rejected execution of coordinating operation
Command: `curl -XPOST https://localhost:9200/logs/_bulk -ku admin:admin --data-binary @logs.json -H "Content-type: application/x-ndjson"`
Message: `{"error":{"root_cause":[{"type":"rejected_execution_exception","reason":"rejected execution of coordinating operation [coordinating_and_primary_bytes=0, replica_bytes=0, all_bytes=0, coordinating_operation_bytes=54215758, max_coordinating_and_primary_bytes=53687091]"}],"type":"rejected_execution_exception","reason":"rejected execution of coordinating operation [coordinating_and_primary_bytes=0, replica_bytes=0, all_bytes=0, coordinating_operation_bytes=54215758, max_coordinating_and_primary_bytes=53687091]"},"status":429}`
Cause: not enough memory
Solution: add more memory in `docker-compose.yml`
