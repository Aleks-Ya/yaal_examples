# PySpark
Python + PySpark + Java

## Build
```
export PYTHON_VERSION=3.7.8
export JAVA_VERSION=11
docker build \
    --tag pyspark-python${PYTHON_VERSION}-java${JAVA_VERSION} \
    --build-arg PYTHON_VERSION=${PYTHON_VERSION} \
    --build-arg JAVA_VERSION=${JAVA_VERSION} \
    .
```

## Run
`docker run -it pyspark-python${PYTHON_VERSION}-java${JAVA_VERSION} bash`
