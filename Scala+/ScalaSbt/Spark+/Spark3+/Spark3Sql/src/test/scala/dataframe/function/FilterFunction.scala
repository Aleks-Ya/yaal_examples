package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.apache.spark.sql.{Row, functions}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FilterFunction extends AnyFlatSpec with Matchers {
  it should "use filter function" in {
    val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(IntegerType)),
      Row("USA", Seq(10, 20, 100, 200)),
      Row("Canada", Seq(30, 40, 300, 400)))
    val updatedDf = df.select(
      col("country"),
      functions.filter(col("orders"), _ > 50) as "big_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","big_orders":[100,200]}""",
      """{"country":"Canada","big_orders":[300,400]}"""
    )
  }
}