# Hadoop with Hive

## Build image
Hadoop version: `2.10.1`  
HadoopBaseImage version: `1`  
Hive version: `2.3.9` (all versions https://apache.volia.net/hive/)  
HiveBaseImage version: `1`  
`./build.sh 2.10.1 1 2.3.9 1`

## Run (for testing)
`docker run -it --rm hive-base-image-2.3.9-hadoop-2.10.1:1 bash`
