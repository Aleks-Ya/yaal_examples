package spark4.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, get, lit}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class GetTest extends AnyFlatSpec with SparkMatchers {
  it should "get an array element by index" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris")),
      Row(List("Berlin", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      get(col("cities"), lit(0)).as("city1"),
      get(col("cities"), lit(1)).as("city2"),
      get(col("cities"), lit(2)).as("city3"))
    updatedDf shouldContain(
      """{"cities":["London","Paris"],"city1":"London","city2":"Paris","city3":null}""",
      """{"cities":["Berlin","Barcelona"],"city1":"Berlin","city2":"Barcelona","city3":null}"""
    )
  }
}