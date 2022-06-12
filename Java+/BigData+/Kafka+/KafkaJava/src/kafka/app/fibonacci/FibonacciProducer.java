package kafka.app.fibonacci;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Producer sends Fibonacci numbers to the topic in form of pair: sequence number - Fibonacci number.
 */
class FibonacciProducer {
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
        var numberCount = 30;
        var host = "localhost";
        var port = 9092;
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
        var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":" + port);
        new FibonacciProducer("fibonacci", numberCount, props).work();
    }

    void work() throws ExecutionException, InterruptedException {
        var props = new Properties();
        producerConfig.forEach(props::put);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());

        Producer<Integer, Long> producer = new KafkaProducer<>(props);
        long f = 1;
        long prev = 0;
        for (var i = 0; i < numberCount; i++) {
            var record = new ProducerRecord<Integer, Long>(topic, i, f);
            var future = producer.send(record);
            var recordMetadata = future.get();
            log.info("Sent: index={}, number={}, metadata={}", i, f, recordMetadata);
            var oldF = f;
            f = prev + f;
            prev = oldF;
        }
        System.out.println("Close producer");
        producer.close();
    }
}
