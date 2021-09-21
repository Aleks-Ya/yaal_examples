# SonarQube scanner for a single-module project

## Docs
- ["SonarScanner for Gradle"](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-gradle/)

## Run as admin
### Prerequisites
1. Run SonarQube Server (`Building+/Docker+/DockerImage+/Application+/Sonar`)
1. Open http://localhost:9000
1. Login as `admin`/`admin` (with password changing)
3. Generate token for `admin` and set `export ADMIN_SONAR_TOKEN=xxxx`

### Execute
Run: `gradle --info clean sonarqube -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$ADMIN_SONAR_TOKEN`

## Run as non-admin user
### Prerequisites
1. Run SonarQube Server (`Building+/Docker+/DockerImage+/Application+/Sonar`)
1. Open http://localhost:9000
1. Login as `admin`/`admin` (with password changing)
1. Revoke `Create Projects` and `Execute Analysis` permissions from `Anyone` group
1. Create a user `john` (any password)
1. Grant `Create Projects` and`Execute Analysis` permissions to `john` user
1. Generate token for `john` and set `export JOHN_SONAR_TOKEN=xxxx`

### Execute
Run: `gradle --info clean sonarqube -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$JOHN_SONAR_TOKEN`
Run with custom ProjectId and ProjectName:
```
gradle --info sonarqube \
    -Dsonar.projectKey=sonar1 \
    -Dsonar.projectName=SonarQube1 \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login=$JOHN_SONAR_TOKEN
```

## Configure in `~/.gradle/gradle.properties`
(!) Prefix `systemProp` should be added to all property names.
Content of `~/.gradle/gradle.properties`:
```
systemProp.sonar.host.url=http://localhost:9000
systemProp.sonar.login=e6760a4179381b1dd3951df0eb829be254020e5d
```
Run: `gradle sonarqube`