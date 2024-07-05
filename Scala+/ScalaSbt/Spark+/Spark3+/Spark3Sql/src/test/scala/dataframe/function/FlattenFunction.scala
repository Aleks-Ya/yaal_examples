package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, collect_list, flatten}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FlattenFunction extends AnyFlatSpec with Matchers {
  private val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(IntegerType)),
    Row("USA", Seq(10, 20)),
    Row("Canada", Seq(100, 200)),
    Row("USA", Seq(30, 40)),
    Row("Canada", Seq(300, 400)))

  it should "use flatten function in select" in {
    val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(ArrayType(IntegerType))),
      Row("USA", Seq(Seq(10, 20), Seq(30, 40))),
      Row("Canada", Seq(Seq(100, 200), Seq(300, 400))))
    val updatedDf = df.select(col("country"), flatten(col("orders")) as "country_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_orders":[10,20,30,40]}""",
      """{"country":"Canada","country_orders":[100,200,300,400]}""")
  }

  it should "use flatten twice" in {
    val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(ArrayType(ArrayType(IntegerType)))),
      Row("USA", Seq(
        Seq(Seq(11, 21), Seq(31, 41)),
        Seq(Seq(12, 22), Seq(32, 42)))),
      Row("Canada", Seq(
        Seq(Seq(101, 201), Seq(301, 401)),
        Seq(Seq(102, 202), Seq(302, 402)))))
    val updatedDf = df.select(col("country"), flatten(flatten(col("orders"))) as "country_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_orders":[11,21,31,41,12,22,32,42]}""",
      """{"country":"Canada","country_orders":[101,201,301,401,102,202,302,402]}""")
  }

  it should "use flatten and extract a nested field" in {
    val personStruct = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
    val df = Factory.createDf(Map("country" -> StringType, "people" -> ArrayType(ArrayType(personStruct))),
      Row("USA", Seq(Seq(Row("John", 10), Row("Mary", 20)), Seq(Row("Mark", 30), Row("Erick", 40)))),
      Row("Canada", Seq(Seq(Row("Patrick", 100), Row("Buddha", 200)), Seq(Row("Petr", 300), Row("Kant", 400)))
      ))
    val updatedDf = df.select(col("country"), flatten(col("people"))("name") as "country_people")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_people":["John","Mary","Mark","Erick"]}""",
      """{"country":"Canada","country_people":["Patrick","Buddha","Petr","Kant"]}""")
  }

  it should "use flatten function in select with collect_list" in {
    val updatedDf = df.select(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }

  it should "use flatten function in agg" in {
    val updatedDf = df.agg(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }

  it should "fail if flatten is applied to an one-level array" in {
    val df = Factory.createDf(Map("country" -> StringType, "orders" -> ArrayType(ArrayType(IntegerType))),
        Row("USA", Seq(10, 20)),
        Row("Canada", Seq(100, 200)))
      .select(col("country"), flatten(col("orders")) as "country_orders")
    val e = intercept[Exception] {
      df.collect()
    }
    e.getMessage should include("java.lang.Integer is not a valid external type for schema of array<int>")
  }
}