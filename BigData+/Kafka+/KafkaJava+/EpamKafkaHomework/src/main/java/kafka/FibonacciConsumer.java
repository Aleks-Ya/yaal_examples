package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class FibonacciConsumer {

    public static void main(String[] args) {
        int printEachRecords = 5;
        String host = "localhost";
        int port = 4242;
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

        Properties props = new Properties();
        props.put("bootstrap.servers", host + ":" + port);
        props.put("acks", "all");
        props.put("retries", "0");
        props.put("batch.size", "16384");
        props.put("linger.ms", "1");
        props.put("buffer.memory", "33554432");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");

        Consumer<Integer, Long> consumer = new KafkaConsumer<>(props);
        List<String> topics = Collections.singletonList("je18-default");
        consumer.subscribe(topics);
        //noinspection InfiniteLoopStatement
        while (true) {
            ConsumerRecords<Integer, Long> records = consumer.poll(100);
            long sum = 0;
            for (ConsumerRecord<Integer, Long> record : records) {
                Integer index = record.key();
                Long fibonacci = record.value();
                if (index % printEachRecords == 0) {
                    System.out.println("Sum: " + sum);
                    sum = 0;
                } else {
                    sum += fibonacci;
                }
            }
        }
    }

}
