package spark.sql.kafka.read

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class KafkaReadTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "consume all existing records from Kafka topic to DataFrame (String column)" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "historical key 1", "historical message 1")
      EmbeddedKafka.publishToKafka(topic, "historical key 2", "historical message 2")

      val messageDf: DataFrame = Factory.ss.read.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .option("endingOffsets", "latest")
        .load()
      messageDf.schema.toDDL shouldEqual "key BINARY,value BINARY,topic STRING,partition INT,offset BIGINT,timestamp TIMESTAMP,timestampType INT"

      val df: DataFrame = messageDf.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      df.schema.toDDL shouldEqual "key STRING,value STRING"

      df.toJSON.collect should contain inOrderOnly(
        """{"key":"historical key 1","value":"historical message 1"}""",
        """{"key":"historical key 2","value":"historical message 2"}""")
    }
  }

  it should "consume all existing records from Kafka topic to DataFrame (StructType column)" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "key 1", """{"name":"John","age":30}""")
      EmbeddedKafka.publishToKafka(topic, "key 2", """{"name":"Mary","age":25}""")

      val messageDf: DataFrame = Factory.ss.read.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .option("endingOffsets", "latest")
        .load()
      messageDf.schema.toDDL shouldEqual "key BINARY,value BINARY,topic STRING,partition INT,offset BIGINT,timestamp TIMESTAMP,timestampType INT"

      val schema = StructType.fromDDL("name STRING, age INT")
      val df: DataFrame = messageDf
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
        .withColumn("parsed", from_json(col("value"), schema))
      df.schema.toDDL shouldEqual "key STRING,value STRING,parsed STRUCT<name: STRING, age: INT>"

      df.toJSON.collect should contain inOrderOnly(
        """{"key":"key 1","value":"{\"name\":\"John\",\"age\":30}","parsed":{"name":"John","age":30}}""",
        """{"key":"key 2","value":"{\"name\":\"Mary\",\"age\":25}","parsed":{"name":"Mary","age":25}}""")
    }
  }

  it should "consume all existing records from Kafka topic to Dataset" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, "key 1", """{"name":"John","age":30}""")
      EmbeddedKafka.publishToKafka(topic, "key 2", """{"name":"Mary","age":25}""")

      val messageDf: DataFrame = Factory.ss.read.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .option("endingOffsets", "latest")
        .load()
      messageDf.schema.toDDL shouldEqual "key BINARY,value BINARY,topic STRING,partition INT,offset BIGINT,timestamp TIMESTAMP,timestampType INT"

      val schema = Encoders.product[Person].schema
      val df: DataFrame = messageDf
        .select(col("key").cast(StringType), col("value").cast(StringType))
        .withColumn("parsed", from_json(col("value"), schema))
      df.schema.toDDL shouldEqual "key STRING,value STRING,parsed STRUCT<name: STRING, age: INT>"

      implicit val encoder: Encoder[DataRow] = Encoders.product[DataRow]
      val ds: Dataset[DataRow] = df.as[DataRow]

      val act: Array[DataRow] = ds.collect
      act should contain inOrderOnly(
        DataRow("key 1", """{"name":"John","age":30}""", Person("John", 30)),
        DataRow("key 2", """{"name":"Mary","age":25}""", Person("Mary", 25)))
    }
  }

}

case class Person(name: String, age: Int)

case class DataRow(key: String, value: String, parsed: Person)