package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.collect_list
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class CollectListTest extends AnyFlatSpec with Matchers {
  it should "use collect_set function" in {
    val df = Factory.createDf("country STRING, city STRING",
      Row("UK", "London"),
      Row("France", "Paris"),
      Row("UK", "Birmingham"),
      Row("UK", "Birmingham") // list preserves duplicates
    )
    val updatedDf: DataFrame = df.groupBy("country").agg(collect_list("city").as("cities"))
    updatedDf.schema.toDDL shouldEqual "country STRING,cities ARRAY<STRING> NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"France","cities":["Paris"]}""",
      """{"country":"UK","cities":["London","Birmingham","Birmingham"]}"""
    )
  }
}