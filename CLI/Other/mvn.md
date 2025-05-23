# mvn CLI (Maven)

## Info
Help: `mvn -h`
Version: `mvn -v`

## Dependencies
Print the dependency tree to a file: `mvn dependency:tree -DoutputFile=target/tree.txt`
Download an artifact from the remote repo to the local repo:
```
mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.1:get -Dartifact=org.apache.orc:orc-tools:1.6.2
```
Download an artifact from the remote repo to local and copy it to a directory (path to target dir is created if absent):
```
mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.1:copy -Dartifact=org.apache.orc:orc-tools:1.6.2 -DoutputDirectory=/tmp/artifacts
```
Install jar to local repo (create checksums):
```
mvn install:install-file \
	-DcreateChecksum=true \
	-Dfile=c:\alex\files\SAP\ngdbc.jar \
	-DgroupId=com.sap.db.jdbc \
	-DartifactId=ngdbc \
	-Dversion=2.4.70 \
	-Dpackaging=jar
```
Build JAR and deploy to Local repo (does NOT create checksums): `mvn clean install`
Download sources: `mvn dependency:sources`
Download JavaDoc: `mvn dependency:resolve -Dclassifier=javadoc`
Go offline: `mvn dependency:go-offline`
Debug, verbose: `mvn -X compile`

## Help plugin
Generate effective POM: `mvn help:effective-pom`
Generate effective Settings: `mvn help:effective-settings`
Print lifecycle which includes given phase: `mvn help:describe -Dcmd=install`

## Tests
Compile tests: `mvn test-compile`
Skip tests: `mvn -DskipTests package`
Run single test class: `mvn -Dtest="my.code.MyTest" test`
Run several test classes: `mvn -Dtest="my.code.FirstTest,my.code.SecondTest" test`

## Prifles
Print active profiles: `mvn help:active-profiles`

## Archetype
Docs ("Maven Archetype Plugin"): https://maven.apache.org/archetype/maven-archetype-plugin/index.html
Help: `mvn archetype:help`

## Exec
Execute a command: `mvn exec:exec -Dexec.executable="echo" -Dexec.args="Hello World"`