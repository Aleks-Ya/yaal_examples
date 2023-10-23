# Elasticsearch, Logstash, Kibana (ELK) Docker image

## Info
DockerHub: https://hub.docker.com/r/sebp/elk
Docs: https://elk-docker.readthedocs.io/

## REST API
See Postman `ElasticSearch7` collection

## Run
### ELK 7
1. Run container
   1. ELK 711
      1. Without persistence: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --rm --name elk7 sebp/elk:711`
      2. With persistence: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --rm --name elk7 -v elk-data:/var/lib/elasticsearch sebp/elk:711`
   2. ELK 7.17.5 (LogStash doesn't work)
       1. Without persistence: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -e LOGSTASH_START=0 -m 2GB -it --rm --name elk7 sebp/elk:7.17.5`
       2. With persistence: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -e LOGSTASH_START=0 -m 2GB -it --rm --name elk7 -v elk-data:/var/lib/elasticsearch sebp/elk:7.17.5`
2. Check ElasticSearch endpoint: http://localhost:9200
3. Open Kibana UI: http://localhost:5601

## Errors
### Max virtual memory areas is too low (WSL)
Message:
```
[2023-08-15T10:08:34,175][ERROR][o.e.b.Bootstrap          ] [elk] node validation exception
[1] bootstrap checks failed
[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```
Fix:
1. Add into `/etc/sysctl.conf` line `vm.max_map_count=262144`
2. Activate chages: `sudo sysctl -p`

### ES can't start without errors (CMD)
Message:
```
waiting for Elasticsearch to be up (29/30)
waiting for Elasticsearch to be up (30/30)
Couldn't start Elasticsearch. Exiting.
Elasticsearch log follows below.
```

Reason: 
It's not enough time for ElasticSearch to start normally. 
Source: https://elk-docker.readthedocs.io/#es-not-starting-timeout

Fix: increment timetout by adding Docker env var `-e ES_CONNECT_RETRY=600`

### ElasticSearch doesn't start
Endpoint http://localhost:9200 is not available.
Message:
```
[2023-10-19T13:00:53,907][WARN ][logstash.outputs.elasticsearch][main] Attempted to resurrect connection to dead ES instance, but got an error {:url=>"http://localhost:9200/", :exception=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :message=>"Elasticsearch Unreachable: [http://localhost:9200/][Manticore::SocketException] Connect to localhost:9200 [localhost/127.0.0.1] failed: Connection refused (Connection refused)"}
```
Fix:
Disable LogStash: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --rm -e LOGSTASH_START=0 --name elk sebp/elk:7.17.5`