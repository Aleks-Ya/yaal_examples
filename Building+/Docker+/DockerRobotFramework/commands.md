# Testing ElasticSearch with RobotFramework

## 1. Run ElasticSearch
Source: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html


```
export ELASTIC_VERSION=5.4.3

docker run -p 9200:9200 \
  -e "http.host=0.0.0.0"  \
  -e "transport.host=127.0.0.1" \
  docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}
```

Test: http://localhost:9200

## 2. Run RobotFramework
Source: https://hub.docker.com/r/ppodgorsek/robot-framework/

```
export ROBOT_VERSION=3.0.2
export ROBOT_REPORTS_FOLDER=reports
export ROBOT_TESTS_FOLDER=tests

docker run -v ${ROBOT_REPORTS_FOLDER}:/opt/robotframework/reports:Z \
  -v ${ROBOT_TESTS_FOLDER}:/opt/robotframework/tests:Z \
  ppodgorsek/robot-framework:${ROBOT_VERSION}
```
