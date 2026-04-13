package spark4.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, concat_ws}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class ConcatWsTest extends AnyFlatSpec with Matchers {

  it should "concatenate strings from an array column" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris")),
      Row(List("Berlin", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      concat_ws(", ", col("cities")).as("joined"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"cities":["London","Paris"],"joined":"London, Paris"}""",
      """{"cities":["Berlin","Barcelona"],"joined":"Berlin, Barcelona"}"""
    )
  }

  it should "concatenate multiple string columns" in {
    val df = Factory.createDf(Map("city1" -> StringType, "city2" -> StringType),
      Row("London", "Paris"),
      Row("Berlin", "Barcelona"))
    val updatedDf = df.select(
      col("city1"),
      col("city2"),
      concat_ws(", ", col("city1"), col("city2")).as("joined"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city1":"London","city2":"Paris","joined":"London, Paris"}""",
      """{"city1":"Berlin","city2":"Barcelona","joined":"Berlin, Barcelona"}"""
    )
  }

}