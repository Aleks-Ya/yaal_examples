package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, concat_ws}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConcatWsTest extends AnyFlatSpec with Matchers {
  it should "use concat_ws function" in {
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
}