# KafkaJava
## Build 
`mvn clean package`
Output: `target/Fibonacci-jar-with-dependencies.jar`

## Run producer
Default parameters (numberCount=30, host=localhost, port=9092):
`java -cp target/Fibonacci-jar-with-dependencies.jar kafka.FibonacciProducer`
Custom parameters (numberCount, host, port):
`java -cp target/Fibonacci-jar-with-dependencies.jar kafka.FibonacciProducer 40 localhost 9092`


## Run consumer
Default parameters (invokeCallbackEachNRecords=1, host=localhost, port=9092):
`java -cp target/Fibonacci-jar-with-dependencies.jar kafka.FibonacciConsumer`
Custom parameters (invokeCallbackEachNRecords, host, port):
`java -cp target/Fibonacci-jar-with-dependencies.jar kafka.FibonacciConsumer 5 localhost 9092`
