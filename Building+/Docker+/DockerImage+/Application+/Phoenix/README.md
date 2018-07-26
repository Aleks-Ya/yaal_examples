# Run Apache Phoenix in Docker

Source: https://hub.docker.com/r/boostport/hbase-phoenix-all-in-one/

## Run
`docker run -it --name phoenix -p 8765:8765 boostport/hbase-phoenix-all-in-one:1.1.5-4.7.0`

## Connect via JDBC
URL: `jdbc:phoenix:thin:url=http://localhost:8765;serialization=PROTOBUF`
Login and password: <empty>
