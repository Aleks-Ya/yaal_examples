# Nexus OOS v3

## Source
https://github.com/sonatype/docker-nexus3

## Run
```
# Run
docker run -d -p 8081:8081 --name nexus sonatype/nexus3

# Test
curl -u admin:admin123 http://localhost:8081/service/metrics/ping
```
