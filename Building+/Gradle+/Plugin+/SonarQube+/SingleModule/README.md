# SonarQube scanner for a single-module project

## Docs
- ["SonarScanner for Gradle"](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/)
- [Example project](https://github.com/SonarSource/sonar-scanning-examples/blob/master/sonar-scanner-gradle/gradle-basic/build.gradle)

## Run as admin
### Prerequisites
1. Run SonarQube Server (`Building+/Docker+/DockerImage+/Application+/Sonar`)
2. Open http://localhost:9000
3. Login as `admin`/`admin` (with password changing)
4. Generate token for `admin` and set `export ADMIN_SONAR_TOKEN=xxxx`

### Execute
Run: `./gradlew --info sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=$ADMIN_SONAR_TOKEN`

## Run as non-admin user
### Prerequisites
1. Run SonarQube Server (`Building+/Docker+/DockerImage+/Application+/Sonar`)
2. Open http://localhost:9000
3. Login as `admin`/`admin` (with password changing)
4. Revoke `Create Projects` and `Execute Analysis` permissions from `Anyone` group
5. Create a user `john` (any password)
6. Grant `Create Projects` and`Execute Analysis` permissions to `john` user
7. Generate token for `john` and set `export JOHN_SONAR_TOKEN=xxxx`

### Execute
Run: `./gradlew --info sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=$JOHN_SONAR_TOKEN`
Run with custom ProjectId and ProjectName:
```shell
./gradlew --info sonar \
    -Dsonar.projectKey=sonar1 \
    -Dsonar.projectName=SonarQube1 \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.token=$JOHN_SONAR_TOKEN
```

## Configure in `~/.gradle/gradle.properties`
(!) Prefix `systemProp` should be added to all property names.
Content of `~/.gradle/gradle.properties`:
```properties
systemProp.sonar.host.url=http://localhost:9000
systemProp.sonar.token=e6760a4179381b1dd3951df0eb829be254020e5d
```
Run: `./gradlew sonar`