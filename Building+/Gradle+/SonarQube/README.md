# SonarQube scanner

## Docs
- ["SonarScanner for Gradle"](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/)

## Run
### Prerequisites
Run Sonar Server (`Building+/Docker+/DockerImage+/Application+/Sonar`)

### Configure in command line
Run: `gradle sonarqube -Dsonar.host.url=http://localhost:9000 -Dsonar.login=e6760a4179381b1dd3951df0eb829be254020e5d`

### Configure in `~/.gradle/gradle.properties`
(!) Prefix `systemProp` should be added to all property names.
Content of `~/.gradle/gradle.properties`:
```
systemProp.sonar.host.url=http://localhost:9000
systemProp.sonar.login=e6760a4179381b1dd3951df0eb829be254020e5d
```
Run: `gradle sonarqube`