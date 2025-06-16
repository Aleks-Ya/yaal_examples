package dataframe.create.kafka.read

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class KafkaReadTest extends AnyFlatSpec with EmbeddedKafka with Matchers {
  it should "consume all existing records from Kafka topic to DataFrame" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "historical key 1", "historical message 1")
      EmbeddedKafka.publishToKafka(topic, "historical key 2", "historical message 2")

      val df = Factory.ss.read.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .option("endingOffsets", "latest")
        .load()
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

      df.toJSON.collect should contain inOrderOnly(
        """{"key":"historical key 1","value":"historical message 1"}""",
        """{"key":"historical key 2","value":"historical message 2"}""")
    }
  }
}
