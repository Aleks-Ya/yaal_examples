
package app.yarn.scheduler.capacity

import org.apache.spark.sql.SparkSession

import java.time.Instant
import java.time.temporal.ChronoUnit
import scala.util.Random

/**
 * Spark app which works specified number of seconds.
 */
object LongRunningApp {
  def main(args: Array[String]): Unit = {
    val startTime = Instant.now()
    val endTime = startTime.plus(1, ChronoUnit.MINUTES)
    val builder = SparkSession.builder().appName("LongRunningApp").master("local[2]")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    val ss = builder.getOrCreate()

    while (Instant.now().isBefore(endTime)) {
      val numbers = Seq.fill(10000000)(Random.nextInt)
      val evenNumbers = ss.sparkContext.parallelize(numbers).filter(number => number % 2 == 0).count()
      val oddNumbers = ss.sparkContext.parallelize(numbers).filter(number => number % 2 != 0).count()
      println(s"Even number count: $evenNumbers")
      println(s"Odd number count: $oddNumbers")
    }
    val finishTime = Instant.now()
    println(s"Finished: startTime=$startTime, finishTime=$finishTime")
  }
}
