# Nexus OOS v3

## Source
https://github.com/sonatype/docker-nexus3

## Run
1. Run: `docker run --rm -it -p 8081:8081 --name nexus sonatype/nexus3`
2. Export credentials:
    ```shell
    export REST=http://localhost:8081/service/rest/v1
    export NEXUS_USER=admin
    export NEXUS_PASS=$(docker exec nexus cat /nexus-data/admin.password)
    export API="-u ${NEXUS_USER}:${NEXUS_PASS} ${REST}"
    ```
3. Test: `curl -i ${API}/status` (status should be 200)

## UI
Url: http://localhost:8081
Login/password: `$NEXUS_USER`/`$NEXUS_PASS`

## mvn CLI
Download artifact from Nexus to local repo:
`mvn -U org.apache.maven.plugins:maven-dependency-plugin:3.9.0:get -s settings.xml -Dartifact=org.apache.orc:orc-tools:1.6.2`

## REST API
Docs: https://help.sonatype.com/repomanager3/rest-and-integration-api

### Assets
List assets: `curl ${API}/assets?repository=maven-central`
Search assets:
- By groupId: `curl ${API}/search/assets?group=org.apache.maven.plugins`
- By artifactId: `curl ${API}/search/assets?name=maven-plugins`
- By version: `curl ${API}/search/assets?version=34`
- By extension: `curl ${API}/search/assets?maven.extension=pom`
- By several params (joined by OR): `curl -i ${API}/search/assets?group=org.apache.maven.plugins&name=maven-plugins&version=34&extension=pom`
