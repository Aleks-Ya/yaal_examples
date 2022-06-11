package kafka.embedded_kafka.inject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
class EmbeddedKafka2Test {
    @Value("${spring.embedded.kafka.brokers}")
    private String brokerAddresses;

    @Test
    void test() {
        System.out.println(brokerAddresses);
    }
}
