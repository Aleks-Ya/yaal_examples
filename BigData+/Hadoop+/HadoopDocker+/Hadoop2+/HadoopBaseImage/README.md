# Hadoop Base Image

## Documentation
https://hadoop.apache.org/docs/r2.10.1/hadoop-project-dist/hadoop-common/SingleCluster.html

## Build image
Available Hadoop versions list: http://apache-mirror.rbc.ru/pub/apache/hadoop/common/

### Build Hadoop 2.10.1 
Build: `./build.sh 2.10.1 1`  
Result: Docker image `hadoop-base-image-2.10.1:1`

## Run (for testing)
`docker run -it --rm hadoop-base-image-2.10.1:1 bash`
