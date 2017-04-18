package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Consumer receives Fibonacci numbers from the Kafka topic and invokes the callback each invokeCallbackEachNRecords.
 *
 * @author Aleksey Yablokov
 */
@SuppressWarnings("WeakerAccess")
public class FibonacciConsumer {
    private static final Logger log = LoggerFactory.getLogger(FibonacciConsumer.class);
    private final int invokeCallbackEachNRecords;
    private final String brokerHost;
    private final int brokerPort;
    private volatile boolean stopped;
    private final String topic;

    /**
     * Instantiate the Consumer.
     *
     * @param topic                      The Kafka's topic.
     * @param invokeCallbackEachNRecords For each invokeCallbackEachNRecords numbers the Consumer will print sum to console.
     * @param brokerHost                 Kafka's broker host.
     * @param brokerPort                 Kafka's broker port.
     */
    public FibonacciConsumer(String topic, int invokeCallbackEachNRecords, String brokerHost, int brokerPort) {
        this.invokeCallbackEachNRecords = invokeCallbackEachNRecords;
        this.brokerHost = brokerHost;
        this.brokerPort = brokerPort;
        this.topic = topic;
    }

    /**
     * Run FibonacciConsumer with default or custom parameters and prints to console sum of Fibonacci numbers
     * each invokeCallbackEachNRecords.
     *
     * @param args Parameters. See README.txt
     */
    public static void main(String[] args) {
        int printEachRecords = 1;
        String host = "localhost";
        int port = 9092;
        if (args.length > 0) {
            try {
                printEachRecords = Integer.parseInt(args[0]);
                host = args[1];
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                log.error("Can't parse arguments: " + Arrays.deepToString(args));
            }
        }
        log.info("Parameters: invokeCallbackEachNRecords={}, host={}, port={}", printEachRecords, host, port);

        new FibonacciConsumer("fibonacci", printEachRecords, host, port)
                .work(System.out::println);
    }

    /**
     * Start receiving.
     *
     * @param callback Each invokeCallbackEachNRecords the callback will be invoked. Value is sum of fibonacci numbers.
     */
    public void work(java.util.function.Consumer<Long> callback) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerHost + ":" + brokerPort);
        props.put("group.id", "group0");
        props.put("client.id", "consumer0");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", IntegerDeserializer.class.getName());
        props.put("value.deserializer", LongDeserializer.class.getName());

        Consumer<Integer, Long> consumer = new KafkaConsumer<>(props);
        List<String> topics = Collections.singletonList(topic);
        consumer.subscribe(topics);
        //noinspection InfiniteLoopStatement
        long sum = 0;
        while (!stopped) {
            ConsumerRecords<Integer, Long> records = consumer.poll(100);
            for (ConsumerRecord<Integer, Long> record : records) {
                Integer index = record.key();
                Long fibonacci = record.value();
                log.info("Received: {}-{}", index, fibonacci);
                sum += fibonacci;
                log.info("Fibonacci sum: " + sum);
                if (index % invokeCallbackEachNRecords == 0) {
                    callback.accept(sum);
                }
            }
        }
    }

    /**
     * Stop receiving.
     */
    public void stop() {
        this.stopped = true;
    }
}
