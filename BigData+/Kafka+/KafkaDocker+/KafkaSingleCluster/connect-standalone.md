# Kafka Connect Standalone

## Configuration
### Kafka connection
Location: `/opt/kafka_2.12-2.4.1/config/connect-standalone.properties`.
Insert into `bootstrap.servers=...` brokers list by `$(broker-list.sh)`.
### File to Topic
Location: `/opt/kafka_2.12-2.4.1/config/connect-file-source.properties`.
Content: no changes are needed
### Topic to File
Location: `/opt/kafka_2.12-2.4.1/config/connect-file-sink.properties`
Content: no changes are needed

## Run
### File to Topic only
```
connect-standalone.sh \
	/opt/kafka_2.12-2.4.1/config/connect-standalone.properties \
	/opt/kafka_2.12-2.4.1/config/connect-file-source.properties
```
### Topic to File only
```
connect-standalone.sh \
	/opt/kafka_2.12-2.4.1/config/connect-standalone.properties \
	/opt/kafka_2.12-2.4.1/config/connect-file-sink.properties
```
### Both
```
connect-standalone.sh \
	-daemon \
	/opt/kafka_2.12-2.4.1/config/connect-standalone.properties \
	/opt/kafka_2.12-2.4.1/config/connect-file-source.properties \
	/opt/kafka_2.12-2.4.1/config/connect-file-sink.properties
```

## Verify
1. Add message to the source file: `echo "my message" >> /test.txt`
2. Read messages from the sink file: `cat /test.sink.txt`

## Kill the deamon
1. Find the app: `ps -A`
2. Kill the java process: `kill 1234`

## Logs
1. `$KAFKA_HOME/logs/connect.log`
2. `$KAFKA_HOME/logs/connectStandalone.out`