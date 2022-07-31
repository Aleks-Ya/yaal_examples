package kafka.embedded_kafka.inject;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
class EmbeddedKafka1Test {
    @Test
    void test(EmbeddedKafkaBroker broker) {
        String brokerList = broker.getBrokersAsString();
        System.out.println(brokerList);
    }
}
