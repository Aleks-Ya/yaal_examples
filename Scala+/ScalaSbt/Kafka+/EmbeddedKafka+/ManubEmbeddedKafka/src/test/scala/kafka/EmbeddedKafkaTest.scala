package kafka

import net.manub.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class EmbeddedKafkaTest extends AnyFlatSpec with EmbeddedKafka with Matchers {
  "Processor" should "" in {
    withRunningKafka {
      val topic = "alert"
      val message = "Hi man"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, message)
      implicit val deserializer: StringDeserializer = new StringDeserializer()
      val from = EmbeddedKafka.consumeFirstStringMessageFrom(topic)
      println(from)
      from shouldEqual message
    }
  }
}
