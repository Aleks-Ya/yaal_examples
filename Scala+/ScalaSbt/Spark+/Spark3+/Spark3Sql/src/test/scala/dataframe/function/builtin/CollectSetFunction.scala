package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.collect_set
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectSetFunction extends AnyFlatSpec with Matchers {
  it should "use collect_set function" in {
    val df = Factory.createDf("city STRING",
      Row("London"),
      Row("Paris"),
      Row("London")
    )
    val updatedDf = df.groupBy("city").agg(collect_set("city").as("cities"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"London","cities":["London"]}""",
      """{"city":"Paris","cities":["Paris"]}"""
    )
  }
}