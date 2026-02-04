package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.collect_list
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectListTest extends AnyFlatSpec with Matchers {
  it should "use collect_list function" in {
    val df = Factory.createDf("city STRING",
      Row("London"),
      Row("Paris"),
      Row("London")
    )
    val updatedDf = df.groupBy("city").agg(collect_list("city").as("cities"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"London","cities":["London","London"]}""",
      """{"city":"Paris","cities":["Paris"]}"""
    )
  }
}