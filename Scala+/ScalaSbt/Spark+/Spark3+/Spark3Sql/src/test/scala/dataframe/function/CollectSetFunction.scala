package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.collect_set
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectSetFunction extends AnyFlatSpec with Matchers {
  it should "use collect_list function" in {
    val df = Factory.createDf(Map("city" -> StringType),
      Row("London"),
      Row("Paris"),
      Row("London")
    )
    val updatedDf = df.groupBy("city").agg(collect_set("city").as("cities"))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","cities":["London"]}""",
      """{"city":"Paris","cities":["Paris"]}"""
    )
  }
}