# Testing ElasticSearch with RobotFramework

## 1. Run ElasticSearch
See DockerElasticSearch project in yaal-examples.

## 2. Run RobotFramework
Source: https://hub.docker.com/r/ppodgorsek/robot-framework/

```
export ROBOT_VERSION=3.0.2
export ROBOT_REPORTS_FOLDER=${PWD}/reports
export ROBOT_TESTS_FOLDER=${PWD}/tests

docker run -v ${ROBOT_REPORTS_FOLDER}:/opt/robotframework/reports:Z \
  -v ${ROBOT_TESTS_FOLDER}:/opt/robotframework/tests:Z \
  ppodgorsek/robot-framework:${ROBOT_VERSION}
```
