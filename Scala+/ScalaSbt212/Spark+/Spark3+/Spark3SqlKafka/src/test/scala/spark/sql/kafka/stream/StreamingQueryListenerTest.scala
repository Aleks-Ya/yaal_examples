package spark.sql.kafka.stream

import factory.Factory
import io.github.embeddedkafka.EmbeddedKafka
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.apache.spark.sql.streaming.{OutputMode, StreamingQueryListener}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.atomic.AtomicInteger
import scala.language.postfixOps


class StreamingQueryListenerTest extends AnyFlatSpec with EmbeddedKafka with Matchers {

  it should "invoke listener for each batch" in {
    withRunningKafka {
      val topic = "topic1"
      implicit val serializer: StringSerializer = new StringSerializer()
      EmbeddedKafka.publishToKafka(topic, Seq(
        ("historical key 1", "historical message 1"),
        ("historical key 2", "historical message 2")
      ))

      val ss = Factory.ss
      ss.streams.addListener(PrintListener)
      PrintListener.startedCounter.get() shouldBe 0
      PrintListener.progressCounter.get() shouldBe 0
      PrintListener.terminatedCounter.get() shouldBe 0
      val df = ss.readStream.format("kafka")
        .option("subscribe", topic)
        .option("kafka.bootstrap.servers", "localhost:6001")
        .option("startingOffsets", "earliest")
        .load
        .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

      val query = df.writeStream
        .outputMode(OutputMode.Append)
        .format("console")
        .start

      query.processAllAvailable()

      PrintListener.startedCounter.get() shouldBe 1
      PrintListener.progressCounter.get() should be > 0
      PrintListener.terminatedCounter.get() shouldEqual 0
    }
  }
}

private object PrintListener extends StreamingQueryListener {
  val startedCounter = new AtomicInteger
  val progressCounter = new AtomicInteger
  val terminatedCounter = new AtomicInteger

  override def onQueryStarted(event: QueryStartedEvent): Unit = {
    startedCounter.incrementAndGet()
    println("Query started")
  }

  override def onQueryProgress(event: QueryProgressEvent): Unit = {
    progressCounter.incrementAndGet()
    println("Query progress")
  }

  override def onQueryTerminated(event: QueryTerminatedEvent): Unit = {
    terminatedCounter.incrementAndGet()
    println("Query terminated")
  }
}
