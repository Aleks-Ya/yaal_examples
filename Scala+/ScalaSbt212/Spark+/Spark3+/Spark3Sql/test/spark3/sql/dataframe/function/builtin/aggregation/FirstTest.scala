package spark3.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.first
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class FirstTest extends AnyFlatSpec with Matchers {
  private val df = Factory.createDf("country STRING, order INT",
    Row("USA", 10),
    Row("Canada", 100),
    Row("USA", 20),
    Row("Canada", 200))

  it should "use first function in groupBy and aggregation" in {
    val updatedDf: DataFrame = df.groupBy("country").agg(
      first("country") as "first_country",
      first("order") as "first_order")
    updatedDf.schema.toDDL shouldEqual "country STRING,first_country STRING,first_order INT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"Canada","first_country":"Canada","first_order":100}""",
      """{"country":"USA","first_country":"USA","first_order":10}"""
    )
  }

  it should "use first function in aggregation" in {
    val updatedDf: DataFrame = df.agg(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf.schema.toDDL shouldEqual "first_country STRING,first_order INT"
    updatedDf.toJSON.collect should contain only """{"first_country":"USA","first_order":10}"""
  }

  it should "use first function in select" in {
    val updatedDf: DataFrame = df.select(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf.schema.toDDL shouldEqual "first_country STRING,first_order INT"
    updatedDf.toJSON.collect should contain only """{"first_country":"USA","first_order":10}"""
  }
}