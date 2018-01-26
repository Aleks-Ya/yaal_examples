# SonarQube

## Run Server
[Link](https://store.docker.com/images/sonarqube)

Run: `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube`

UI: http://localhost:9000/about

Credentials: admin/admin

## Run Analysis
From Maven:

```
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=a35a8a127c0a07840febffff213b8703ff55fa86
```

## Narrowing the Focus (in Maven)
[Documentation](https://docs.sonarqube.org/display/SONAR/Narrowing+the+Focus)
### Ignore code blocks
```
<!-- pom.xml -->
<properties>
    <sonar.issue.ignore.block>my</sonar.issue.ignore.block>
    <sonar.issue.ignore.block.my.beginBlockRegexp>SONAR_OFF</sonar.issue.ignore.block.my.beginBlockRegexp>
    <sonar.issue.ignore.block.my.endBlockRegexp>SONAR_ON</sonar.issue.ignore.block.my.endBlockRegexp>
</properties>

/* Java: */
// SONAR_OFF
System.out.println("Don't check me!");
// SONAR_ON
```
### Ignore concrete rules
```
<!-- Ignore System.out.println (squid:S106) and many arguments in constructor (squid:S00107) -->
<properties>
    <sonar.issue.ignore.multicriteria>sout,constructor</sonar.issue.ignore.multicriteria>
    <sonar.issue.ignore.multicriteria.sout.ruleKey>squid:S106</sonar.issue.ignore.multicriteria.sout.ruleKey>
    <sonar.issue.ignore.multicriteria.sout.resourceKey>**/*.java</sonar.issue.ignore.multicriteria.sout.resourceKey>
    <sonar.issue.ignore.multicriteria.constructor.ruleKey>squid:S00107</sonar.issue.ignore.multicriteria.constructor.ruleKey>
    <sonar.issue.ignore.multicriteria.constructor.resourceKey>**/*.java</sonar.issue.ignore.multicriteria.constructor.resourceKey>
</properties>
```
### Ignore test coverage
```
<properties>
    <sonar.coverage.exclusions>src/main/java/org/booking/**</sonar.coverage.exclusions>
</properties>
```
### Ignore code duplication
```
<!-- Single value: -->
<properties>
    <sonar.cpd.exclusions>src/**/*.java</sonar.cpd.exclusions>
</properties>

<!-- Multi values: -->
<properties>
    <sonar.cpd.exclusions>src/main/java/booking/EventController.java,src/main/java/booking/UserController.java</sonar.cpd.exclusions>
</properties>
```
