package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.collect_set
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class CollectSetTest extends AnyFlatSpec with SparkMatchers {
  it should "use collect_set function" in {
    val df = Factory.createDf("country STRING, city STRING",
      Row("UK", "London"),
      Row("France", "Paris"),
      Row("UK", "Birmingham"),
      Row("UK", "Birmingham") // set eliminates duplicates
    )
    val updatedDf: DataFrame = df.groupBy("country").agg(collect_set("city").as("cities"))
    updatedDf shouldHaveDDL "country STRING,cities ARRAY<STRING> NOT NULL"
    updatedDf shouldContain(
      """{"country":"France","cities":["Paris"]}""",
      """{"country":"UK","cities":["Birmingham","London"]}"""
    )
  }
}