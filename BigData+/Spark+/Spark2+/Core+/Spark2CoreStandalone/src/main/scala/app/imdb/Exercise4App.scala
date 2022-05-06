
package app.imdb

import org.apache.spark.sql.functions.col

import scala.util.Try

/**
 * Find non-integer values in "runtimeMinutes" column of "title.basics.tsv" file.
 */
object Exercise4App {
  def main(args: Array[String]): Unit = {
    val df = DataFrameFactory.titleBasicsDf
    val rowCount = df.count()
    println(s"Total row count: $rowCount")

    val nonNullDf = df.filter(col("runtimeMinutes").isNotNull)
    val nonNullCount = nonNullDf.count()
    println(s"Non-null row count: $nonNullCount")

    val nonIntDf = nonNullDf.filter(row => Try(row.getString(row.fieldIndex("runtimeMinutes")).toInt).isFailure)
    nonIntDf.printSchema()
    nonIntDf.show()
    val count = nonIntDf.count()
    println(s"Non-Int row count: $count")
  }
}
