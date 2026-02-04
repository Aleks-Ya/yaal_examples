package spark.sql.kafka.stream

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.streaming.StreamingQueryListener
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class PrintBatchOffsetTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "print start and end offset of each batch" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, Seq(
        ("historical key 1", "historical message 1"),
        ("historical key 2", "historical message 2")
      ))

      val ss = Factory.ss
      ss.streams.addListener(new OffsetListener(topic))
      val df = ss.readStream.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .load
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

      val query = df.writeStream
        .format("console")
        .start

      query.processAllAvailable()

      EmbeddedKafka.publishToKafka(topic, Seq(
        ("new key 3", "new message 3"),
        ("new key 4", "new message 4")
      ))

      query.processAllAvailable()
    }
  }
}

private class OffsetListener(private val topic: String) extends StreamingQueryListener {
  private val partition = "0"
  private implicit val formats: DefaultFormats = DefaultFormats

  override def onQueryStarted(event: QueryStartedEvent): Unit = println("Query started")

  override def onQueryProgress(event: QueryProgressEvent): Unit =
    event.progress.sources.foreach { sp =>
      println(s"Source Progress (compact JSON): ${sp.json}")
      println(s"Source Progress (pretty JSON): $sp")
      println(s"Start Offset: ${extractOffsetFromJson(sp.startOffset)}")
      println(s"End Offset: ${extractOffsetFromJson(sp.endOffset)}")
      println(s"Latest Offset: ${extractOffsetFromJson(sp.latestOffset)}")
    }

  private def extractOffsetFromJson(json: String): String =
    try {
      JsonMethods.parseOpt(json)
        .map(_ \ topic \ partition)
        .map(_.extract[Int])
        .map(_.toString)
        .getOrElse("-")
    } catch {
      case e: Exception => s"${e.getClass.getSimpleName}: ${e.getMessage}"
    }

  override def onQueryTerminated(event: QueryTerminatedEvent): Unit = println("Query terminated")
}
