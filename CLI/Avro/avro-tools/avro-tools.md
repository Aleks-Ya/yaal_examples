# avro-tools CLI

## Install
1. Download JAR: `wget https://downloads.apache.org/avro/stable/java/avro-tools-1.12.0.jar -x -O ~/installed/avro-tools/avro-tools.jar`
2. Add to `~/.bashrc`: `alias avro-tools='java -jar ~/installed/avro-tools/avro-tools.jar'`

## Commands
Help: `avro-tools`
Help about a command: `avro-tools cat`

Count records: `avro-tools count my.avro`

## Errors 
### Unable to load native-hadoop library for your platform
Message: `WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable`
Solution:
1. Download Hadoop distribution from https://hadoop.apache.org/releases.html, unpack to `~/installed/hadoop-3`
2. Add ENV var `subl ~/.profile`:
```
export HADOOP_HOME=~/installed/hadoop-3
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$HADOOP_HOME/lib/native
```
3. Logout from Unix user
