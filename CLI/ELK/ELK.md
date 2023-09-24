# Elasticsearch, Logstash, Kibana (ELK) Docker image

DockerHub: https://hub.docker.com/r/sebp/elk

## Run
1. Run container
	1. Without persistance: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --rm --name elk-711 sebp/elk:711`
	2. With persistance: `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --rm --name elk-711 -v elk-data:/var/lib/elasticsearch sebp/elk:711`
2. Open Kibana UI: http://localhost:5601

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
