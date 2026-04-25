package spark4.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, filter, lit}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class FilterTest extends AnyFlatSpec with SparkMatchers {

  it should "use filter function" in {
    val df = Factory.createDf("country STRING, orders ARRAY<INT>",
      Row("USA", Seq(10, 20, 100, 200)),
      Row("Canada", Seq(30, 40, 300, 400)))
    val updatedDf = df.select(
      col("country"),
      filter(col("orders"), _ > 50) as "big_orders")
    updatedDf shouldHaveDDL "country STRING,big_orders ARRAY<INT>"
    updatedDf shouldContain(
      """{"country":"USA","big_orders":[100,200]}""",
      """{"country":"Canada","big_orders":[300,400]}"""
    )
  }

  it should "filter nothing" in {
    val df = Factory.createDf("country STRING, orders ARRAY<INT>",
      Row("USA", Seq(10, 20, 100, 200)),
      Row("Canada", Seq(30, 40, 300, 400)))
    val updatedDf = df.select(
      col("country"),
      filter(col("orders"), _ => lit(true)) as "big_orders")
    updatedDf shouldHaveDDL "country STRING,big_orders ARRAY<INT>"
    updatedDf shouldContain(
      """{"country":"USA","big_orders":[10,20,100,200]}""",
      """{"country":"Canada","big_orders":[30,40,300,400]}"""
    )
  }

}