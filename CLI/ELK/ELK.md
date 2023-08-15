# Elasticsearch, Logstash, Kibana (ELK) Docker image

DockerHub: https://hub.docker.com/r/sebp/elk

Run: 
1. `docker run -p 5601:5601 -p 9200:9200 -p 5044:5044 -it --name elk-711 sebp/elk:711`
2. Open Kibana UI: http://localhost:5601

## Errors
### max virtual memory areas is too low
Message:
```
[2023-08-15T10:08:34,175][ERROR][o.e.b.Bootstrap          ] [elk] node validation exception
[1] bootstrap checks failed
[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```
Fix:
1. Add into `/etc/sysctl.conf` line `vm.max_map_count=262144`
2. Activate chages: `sudo sysctl -p`
