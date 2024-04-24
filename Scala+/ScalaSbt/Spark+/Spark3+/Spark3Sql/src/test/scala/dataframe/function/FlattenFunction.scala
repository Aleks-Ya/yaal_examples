package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{collect_list, flatten}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FlattenFunction extends AnyFlatSpec with Matchers {
  private val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(IntegerType)),
    Row("USA", Seq(10, 20)),
    Row("Canada", Seq(100, 200)),
    Row("USA", Seq(30, 40)),
    Row("Canada", Seq(300, 400)))

  it should "use flatten function in select" in {
    val updatedDf = df.select(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }

  it should "use flatten function in agg" in {
    val updatedDf = df.agg(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }
}