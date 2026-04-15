package spark3.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.collect_list
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class CollectListTest extends AnyFlatSpec with Matchers {
  it should "use collect_list function" in {
    val df = Factory.createDf("city STRING",
      Row("London"),
      Row("Paris"),
      Row("London")
    )
    val updatedDf: DataFrame = df.groupBy("city").agg(collect_list("city").as("cities"))
    updatedDf.schema.toDDL shouldEqual "city STRING,cities ARRAY<STRING> NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"London","cities":["London","London"]}""",
      """{"city":"Paris","cities":["Paris"]}"""
    )
  }
}