Build: mvn clean package
Output: target/Fibonacci-jar-with-dependencies.jar


Run producer:
Default parameters (numberCount=30, host=localhost, port=4242):
java -jar Fibonacci-jar-with-dependencies.jar kafka.FibonacciProducer
Custom parameters (numberCount, host, port):
java -jar Fibonacci-jar-with-dependencies.jar kafka.FibonacciProducer 50 myhost 7777
