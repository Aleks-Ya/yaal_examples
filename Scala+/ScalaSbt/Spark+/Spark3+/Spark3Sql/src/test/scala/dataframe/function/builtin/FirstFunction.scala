package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.first
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FirstFunction extends AnyFlatSpec with Matchers {
  private val df = Factory.createDf(Map("country" -> StringType, "order" -> IntegerType),
    Row("USA", 10),
    Row("Canada", 100),
    Row("USA", 20),
    Row("Canada", 200))

  it should "use first function in groupBy and aggregation" in {
    val updatedDf = df.groupBy("country").agg(
      first("country") as "first_country",
      first("order") as "first_order")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"Canada","first_country":"Canada","first_order":100}""",
      """{"country":"USA","first_country":"USA","first_order":10}"""
    )
  }

  it should "use first function in aggregation" in {
    val updatedDf = df.agg(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf.toJSON.collect should contain only """{"first_country":"USA","first_order":10}"""
  }

  it should "use first function in select" in {
    val updatedDf = df.select(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf.toJSON.collect should contain only """{"first_country":"USA","first_order":10}"""
  }
}