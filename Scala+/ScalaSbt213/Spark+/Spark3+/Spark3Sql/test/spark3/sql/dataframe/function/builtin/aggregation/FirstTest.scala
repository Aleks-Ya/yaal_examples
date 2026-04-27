package spark3.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.first
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class FirstTest extends AnyFlatSpec with SparkMatchers {
  private val df = Factory.createDf("country STRING, order INT",
    Row("USA", 10),
    Row("Canada", 100),
    Row("USA", 20),
    Row("Canada", 200))

  it should "use first function in groupBy and aggregation" in {
    val updatedDf: DataFrame = df.groupBy("country").agg(
      first("country") as "first_country",
      first("order") as "first_order")
    updatedDf shouldHaveDDL "country STRING,first_country STRING,first_order INT"
    updatedDf shouldContain(
      """{"country":"Canada","first_country":"Canada","first_order":100}""",
      """{"country":"USA","first_country":"USA","first_order":10}"""
    )
  }

  it should "use first function in aggregation" in {
    val updatedDf: DataFrame = df.agg(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf shouldHaveDDL "first_country STRING,first_order INT"
    updatedDf shouldContain """{"first_country":"USA","first_order":10}"""
  }

  it should "use first function in select" in {
    val updatedDf: DataFrame = df.select(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf shouldHaveDDL "first_country STRING,first_order INT"
    updatedDf shouldContain """{"first_country":"USA","first_order":10}"""
  }

  it should "process all null values" in {
    val df = Factory.createDf("country STRING, order INT",
      Row("USA", null),
      Row("Canada", null),
      Row("USA", null),
      Row("Canada", null))
    val updatedDf: DataFrame = df.agg(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf shouldHaveDDL "first_country STRING,first_order INT"
    updatedDf shouldContain """{"first_country":"USA","first_order":null}"""
  }

  it should "process some null values" in {
    val df = Factory.createDf("country STRING, order INT",
      Row(null, null),
      Row("Canada", 10))
    val updatedDf: DataFrame = df.agg(
      first("country") as "first_country",
      first("order") as "first_order"
    )
    updatedDf shouldHaveDDL "first_country STRING,first_order INT"
    updatedDf shouldContain """{"first_country":null,"first_order":null}"""
  }

}