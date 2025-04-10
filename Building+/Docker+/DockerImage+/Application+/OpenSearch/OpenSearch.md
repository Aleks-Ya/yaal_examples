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

## Connect with Bash:
User `opesearch`: `docker exec -it opensearch bash`
user `root`: `docker exec -it -u root opensearch bash`


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

### UnsatisfiedLinkError
Command: use vector search
Message:
```
java.lang.UnsatisfiedLinkError: /usr/share/opensearch/plugins/opensearch-knn/lib/libopensearchknn_faiss_avx512.so: /usr/share/opensearch/data/ml_cache/pytorch/1.13.1-cpu-precxx11-linux-x86_64/libstdc++.so.6: version `GLIBCXX_3.4.20' not found (required by /usr/share/opensearch/plugins/opensearch-knn/lib/libopensearchknn_faiss_avx512.so)
```
`yum install binutils`
`ls -l /usr/share/opensearch/data/ml_cache/pytorch/1.13.1-cpu-precxx11-linux-x86_64`
`strings /usr/share/opensearch/data/ml_cache/pytorch/1.13.1-cpu-precxx11-linux-x86_64/libstdc++.so.6 | grep GLIBCXX`
```
bash-5.2# strings /usr/share/opensearch/data/ml_cache/pytorch/1.13.1-cpu-precxx11-linux-x86_64/libstdc++.so.6 | grep GLIBCXX
GLIBCXX_3.4
GLIBCXX_3.4.1
GLIBCXX_3.4.2
GLIBCXX_3.4.3
GLIBCXX_3.4.4
GLIBCXX_3.4.5
GLIBCXX_3.4.6
GLIBCXX_3.4.7
GLIBCXX_3.4.8
GLIBCXX_3.4.9
GLIBCXX_3.4.10
GLIBCXX_3.4.11
GLIBCXX_3.4.12
GLIBCXX_3.4.13
GLIBCXX_3.4.14
GLIBCXX_3.4.15
GLIBCXX_3.4.16
GLIBCXX_3.4.17
GLIBCXX_3.4.18
GLIBCXX_3.4.19
GLIBCXX_DEBUG_MESSAGE_LENGTH
```
```
bash-5.2# ls -l /usr/share/opensearch/data/ml_cache/pytorch/
total 12
drwx------ 2 opensearch opensearch 4096 Apr 10 08:00 1.13.1-cpu-precxx11-linux-x86_64
-rw-rw-r-- 1 opensearch opensearch 4338 Apr 10 07:57 1.13.1.txt
bash-5.2# strings /usr/share/opensearch/data/ml_cache/pytorch/1.13.1-cpu-precxx11-linux-x86_64/libstdc++.so.6 | grep GLIBCXX
```