package kafka

import net.manub.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.scalatest.{FlatSpec, Matchers}


class EmbeddedKafkaTest extends FlatSpec with EmbeddedKafka with Matchers {
  "Processor" should "" in {
    withRunningKafka {
      val topic = "alert"
      val message = "Hi man"
      implicit val serializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, message)
      implicit val deserializer = new StringDeserializer()
      val from = EmbeddedKafka.consumeFirstStringMessageFrom(topic)
      println(from)
      from shouldEqual message
    }
  }
}
