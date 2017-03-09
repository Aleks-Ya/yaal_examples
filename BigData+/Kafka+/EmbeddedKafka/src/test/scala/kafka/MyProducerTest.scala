package kafka

import java.util.Properties

import net.manub.embeddedkafka.EmbeddedKafka
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.scalatest.{FlatSpec, WordSpec}

class MyProducerTest extends FlatSpec with EmbeddedKafka {

  "Processor" should "" in {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("acks", "all")
    props.put("block.on.buffer.full", "true")
    //    val producer = new KafkaProducer(props)
    val producer = new KafkaProducer(props)
    val topic = "fast-messages"
    val value = "This is a dummy message"
    val record = new ProducerRecord(topic, value)
    //    producer.send(record)

  }
}
