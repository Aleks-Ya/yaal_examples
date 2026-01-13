package streaming

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Example from https://spark.apache.org/docs/4.0.0/streaming/getting-started.html
 */
class SocketSourceTest extends AnyFlatSpec with Matchers {
  it should "use Structured Streaming" in {
    // Create DataFrame representing the stream of input lines from connection to localhost:9999
    val lines = Factory.ss.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    import Factory.ss.implicits._
    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()

    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }
}