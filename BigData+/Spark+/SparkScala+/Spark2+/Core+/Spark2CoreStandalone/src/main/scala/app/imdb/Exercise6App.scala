
package app.imdb

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{avg, col, max}

import scala.util.Try

/**
 * Calculate maximum and average movie duration.
 */
object Exercise6App {
  def main(args: Array[String]): Unit = {
    import DataFrameFactory.ss.sqlContext.implicits._
    def runtimeMinutesToInt(row: Row) = row.getString(row.fieldIndex("runtimeMinutes")).toInt

    val aggRow = DataFrameFactory.titleBasicsDf
      .select("runtimeMinutes")
      .filter(col("runtimeMinutes").isNotNull)
      .filter(row => Try(runtimeMinutesToInt(row)).isSuccess)
      .map(row => runtimeMinutesToInt(row))
      .agg(max("value"), avg("value"))
      .first()
    val maxMovieDuration = aggRow.getInt(0)
    val avgMovieDuration = aggRow.getDouble(1).round

    println(s"Max movie duration: $maxMovieDuration minutes")
    println(s"Average movies duration: $avgMovieDuration minutes")
    assert(maxMovieDuration == 51420)
    assert(avgMovieDuration == 44)
  }
}
