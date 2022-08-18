package kafka.consumer.kafka_listener_annotation.json.time;

import java.time.LocalDateTime;

record Person(Long id, String name, LocalDateTime meeting) {
}
