package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_distinct, col}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ArrayDistinctTest extends AnyFlatSpec with SparkMatchers {
  it should "use array_distinct function" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris", "London")),
      Row(List("Berlin", "Barcelona", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      array_distinct(col("cities")) as "unique_city")
    updatedDf shouldContain(
      """{"cities":["London","Paris","London"],"unique_city":["London","Paris"]}""",
      """{"cities":["Berlin","Barcelona","Barcelona"],"unique_city":["Berlin","Barcelona"]}"""
    )
  }
}