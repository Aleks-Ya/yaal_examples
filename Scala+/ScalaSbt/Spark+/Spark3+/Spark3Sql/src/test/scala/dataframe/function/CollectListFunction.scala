package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, collect_list}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectListFunction extends AnyFlatSpec with Matchers {
  it should "use collect_list function" in {
    val df = Factory.createDf(Map("city" -> StringType),
      Row("London"),
      Row("Paris"),
      Row("London")
    )
    val updatedDf = df.groupBy("city").agg(collect_list(col("city")).as("cities"))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"London","cities":["London","London"]}""",
      """{"city":"Paris","cities":["Paris"]}"""
    )
  }
}