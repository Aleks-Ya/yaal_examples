package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Producer sends Fibonacci numbers to the topic in form of pair: sequence number - Fibonacci number.
 */
public class FibonacciProducer {
    private static final Logger log = LoggerFactory.getLogger(FibonacciProducer.class);
    private final int numberCount;
    private final Properties producerConfig;
    private final String topic;

    /**
     * Instantiate {@link FibonacciProducer}.
     *
     * @param topic          Kafka topic name.
     * @param numberCount    How much Fibonacci numbers the producer will send.
     * @param producerConfig Kafka producer configuration.
     */
    public FibonacciProducer(String topic, int numberCount, Properties producerConfig) {
        this.numberCount = numberCount;
        this.producerConfig = producerConfig;
        this.topic = topic;
    }

    /**
     * Run FibonacciProducer with default or custom parameters.
     *
     * @param args FibonacciProducer's parameters. See README.md
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int numberCount = 30;
        String host = "localhost";
        int port = 9092;
        if (args.length > 0) {
            try {
                numberCount = Integer.parseInt(args[0]);
                host = args[1];
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                log.error("Can't parse arguments: " + Arrays.deepToString(args));
            }
        }
        log.info("Parameters: numberCount={}, host={}, port={}", numberCount, host, port);
        Properties props = new Properties();
        props.put("bootstrap.servers", host + ":" + port);
        new FibonacciProducer("fibonacci", numberCount, props).work();
    }

    void work() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        producerConfig.forEach(props::put);
        props.put("key.serializer", IntegerSerializer.class.getName());
        props.put("value.serializer", LongSerializer.class.getName());

        Producer<Integer, Long> producer = new KafkaProducer<>(props);
        long f = 1;
        long prev = 0;
        for (int i = 0; i < numberCount; i++) {
            ProducerRecord<Integer, Long> record = new ProducerRecord<>(topic, i, f);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata recordMetadata = future.get();
            log.info("Sent: index={}, number={}, metadata={}", i, f, recordMetadata);
            long oldF = f;
            f = prev + f;
            prev = oldF;
        }
        System.out.println("Close producer");
        producer.close();
    }
}
