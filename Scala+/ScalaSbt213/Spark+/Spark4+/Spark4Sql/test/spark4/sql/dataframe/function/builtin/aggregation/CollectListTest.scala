package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.collect_list
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class CollectListTest extends AnyFlatSpec with SparkMatchers {
  it should "use collect_set function" in {
    val df = Factory.createDf("country STRING, city STRING",
      Row("UK", "London"),
      Row("France", "Paris"),
      Row("UK", "Birmingham"),
      Row("UK", "Birmingham") // list preserves duplicates
    )
    val updatedDf: DataFrame = df.groupBy("country").agg(collect_list("city").as("cities"))
    updatedDf shouldHaveDDL "country STRING,cities ARRAY<STRING> NOT NULL"
    updatedDf shouldContain(
      """{"country":"France","cities":["Paris"]}""",
      """{"country":"UK","cities":["London","Birmingham","Birmingham"]}"""
    )
  }
}