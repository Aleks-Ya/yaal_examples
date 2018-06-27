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

## Security
Default password: admin/admin123

## API
### Search & Download
```
http://localhost:8081/service/rest/beta/search/assets/download?maven.groupId=com.salesforce.i18n&maven.artifactId=i18n-util&maven.baseVersion=1.0.1&maven.extension=jar
```
