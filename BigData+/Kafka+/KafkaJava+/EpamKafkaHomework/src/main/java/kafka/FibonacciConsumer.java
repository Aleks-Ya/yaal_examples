package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;

public class FibonacciConsumer {
    private final int printEachRecords;
    private final String brokerHost;
    private final int brokerPort;
    private volatile boolean stopped;
    private final String topic;

    public FibonacciConsumer(String topic, int printEachRecords, String brokerHost, int brokerPort) {
        this.printEachRecords = printEachRecords;
        this.brokerHost = brokerHost;
        this.brokerPort = brokerPort;
        this.topic = topic;
    }

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
                System.err.println("Can't parse arguments: " + Arrays.deepToString(args));
            }
        }
        System.out.printf("Parameters: printEachRecords=%d, host=%s, port=%d%n", printEachRecords, host, port);
        new FibonacciConsumer("fibonacci", printEachRecords, host, port).work((index, fibonacci) -> System.out.println(index + "-" + fibonacci));
    }

    void work(BiConsumer<Integer, Long> callback) {
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
        while (!stopped) {
            ConsumerRecords<Integer, Long> records = consumer.poll(100);
            for (ConsumerRecord<Integer, Long> record : records) {
                Integer index = record.key();
                Long fibonacci = record.value();
                if (index % printEachRecords == 0) {
                    callback.accept(index, fibonacci);
                }
            }
        }
    }

    public void stop() {
        this.stopped = true;
    }
}
