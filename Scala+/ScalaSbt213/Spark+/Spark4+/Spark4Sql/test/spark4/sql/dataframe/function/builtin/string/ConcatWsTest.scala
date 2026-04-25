package spark4.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, concat_ws}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ConcatWsTest extends AnyFlatSpec with SparkMatchers {

  it should "concatenate strings from an array column" in {
    val df = Factory.createDf("cities ARRAY<STRING>",
      Row(List("London", "Paris")),
      Row(List("Berlin", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      concat_ws(", ", col("cities")).as("joined"))
    updatedDf shouldHaveDDL "cities ARRAY<STRING>,joined STRING NOT NULL"
    updatedDf shouldContain(
      """{"cities":["London","Paris"],"joined":"London, Paris"}""",
      """{"cities":["Berlin","Barcelona"],"joined":"Berlin, Barcelona"}"""
    )
  }

  it should "concatenate multiple string columns" in {
    val df = Factory.createDf("city1 STRING, city2 STRING",
      Row("London", "Paris"),
      Row("Berlin", "Barcelona"))
    val updatedDf = df.select(
      col("city1"),
      col("city2"),
      concat_ws(", ", col("city1"), col("city2")).as("joined"))
    updatedDf shouldHaveDDL "city1 STRING,city2 STRING,joined STRING NOT NULL"
    updatedDf shouldContain(
      """{"city1":"London","city2":"Paris","joined":"London, Paris"}""",
      """{"city1":"Berlin","city2":"Barcelona","joined":"Berlin, Barcelona"}"""
    )
  }

}